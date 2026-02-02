package com.papa123.debug;
import java.io.RandomAccessFile;

public class Memory {
    // Exemplo de leitura de memória via ROOT (Invisível)
    public static float readFloat(int pid, long address) {
        try {
            RandomAccessFile mem = new RandomAccessFile("/proc/" + pid + "/mem", "r");
            mem.seek(address);
            byte[] bytes = new byte[4];
            mem.read(bytes);
            mem.close();
            // Converte bytes para float (HP/Posição)
            return java.nio.ByteBuffer.wrap(bytes).order(java.nio.ByteOrder.LITTLE_ENDIAN).getFloat();
        } catch (Exception e) { return -1; }
    }
}
