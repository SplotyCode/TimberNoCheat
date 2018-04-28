package me.david.timbernocheat.storage;

import com.google.common.primitives.Longs;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

public class BinarySerializer {

    private ArrayList<Byte> bytes = new ArrayList<>();
    private int index = -1;

    private byte getNextByte(){
        index++;
        return bytes.get(index);
    }

    private byte[] getNextBytes(int amount){
        byte[] bytes = new byte[amount];
        for(int i = 0;i<amount;i++){
            bytes[i] = getNextByte();
        }
        return bytes;
    }

    private void writeBytes(byte[] bytes){
        this.bytes.addAll(Arrays.asList(ArrayUtils.toObject(bytes)));
    }

    public void writeVarInt(int value) {
        byte part;
        do {
            part = (byte) (value & 0x7F);
            value >>>= 7;
            if(value != 0)
                part |= 0x80;
            bytes.add(part);
        } while (value != 0);
    }

    public int readVarInt() {
        int out = 0, bytes = 0;
        byte part;
        do {
            part = getNextByte();
            out |= (part & 0x7F) << (bytes++ * 7);
            if(bytes > 5)
                throw new IllegalArgumentException();
        } while ((part & 0x80) == 0x80);
        return out;
    }

    public <T> T readEnum(Class<T> enumClass) {
        return enumClass.getEnumConstants()[this.readVarInt()];
    }

    public void writeEnum(Enum<?> source) {
        this.writeVarInt(source.ordinal());
    }

    public BinarySerializer readFile(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            bytes = new ArrayList<>(Arrays.asList(ArrayUtils.toObject(IOUtils.toByteArray(fis))));
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void writeFile(File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            IOUtils.write(ArrayUtils.toPrimitive(bytes.toArray(new Byte[bytes.size()])), fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readString() {
        byte[] b = getNextBytes(readVarInt());
        return new String(b, Charset.forName("UTF-8"));
    }

    public void writeString(String string) {
        byte[] abyte = string.getBytes(Charset.forName("UTF-8"));
        this.writeVarInt(abyte.length);
        this.writeBytes(abyte);
    }

    public ArrayList<String> readStringList(){
        int i = readVarInt();
        ArrayList<String> list = new ArrayList<>();
        while(i != 0) {
            list.add(readString());
            i--;
        }
        return list;
    }

    public ArrayList<Integer> readIntList(){
        int i = readVarInt();
        ArrayList<Integer> list = new ArrayList<>();
        while(i != 0) {
            list.add(readVarInt());
            i--;
        }
        return list;
    }

    public void writeIntList(ArrayList<Integer>list) {
        writeVarInt(list.size());
        for(Integer i : list)
            writeVarInt(i);
    }

    public void writeStringList(ArrayList<String>list) {
        writeVarInt(list.size());
        for(String s : list)
            writeString(s);
    }

    public void writeLong(long lon){
        byte[] bytes = Longs.toByteArray(lon);
        writeVarInt(bytes.length);
        writeBytes(bytes);
    }

    public long readLong(){
        return Longs.fromByteArray(getNextBytes(readVarInt()));
    }

    public boolean readBoolean(){
        return getNextByte() != 0;
    }

    public void writeBoolean(boolean bool){
        bytes.add((byte) (bool?1:0));
    }

    public void writeDouble(double value) {
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble(value);
        this.bytes.addAll(Arrays.asList(bytes[0], bytes[1], bytes[2], bytes[3], bytes[4], bytes[5], bytes[6], bytes[7]));
    }

    public double readDouble() {
        return ByteBuffer.wrap(getNextBytes(8)).getDouble();
    }

    public void writeArray(String[] array){
        if(array == null) {
            writeVarInt(0);
            return;
        }
        writeVarInt(array.length);
        for(String str : array) writeString(str);
    }

    public String[] readStringArray(){
        int max = readVarInt();
        String[] array = new String[max];
        for(int i = 0;i < max;i++)
            array[i] = readString();
        return array;
    }

}
