package me.david.timbernocheat.storage;

import java.io.IOException;

public interface BinaryComponent {

    void read(BinarySerializer serializer) throws IOException;
    void write(BinarySerializer serializer) throws IOException;

}
