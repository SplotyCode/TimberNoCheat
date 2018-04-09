package me.david.timbernocheat.storage;

import java.io.IOException;

public interface BinaryComponent {

    void read(BinarySerilizer serializer) throws IOException;
    void write(BinarySerilizer serializer) throws IOException;

}
