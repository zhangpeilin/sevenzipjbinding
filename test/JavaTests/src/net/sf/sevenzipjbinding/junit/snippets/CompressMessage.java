package net.sf.sevenzipjbinding.junit.snippets;

/* BEGIN_SNIPPET(CompressMessage) */
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

import net.sf.sevenzipjbinding.IOutCreateArchiveZip;
import net.sf.sevenzipjbinding.IOutCreateCallback;
import net.sf.sevenzipjbinding.IOutItemZip;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.SevenZipException;
import net.sf.sevenzipjbinding.impl.OutItemFactory;
import net.sf.sevenzipjbinding.impl.RandomAccessFileOutStream;
import net.sf.sevenzipjbinding.util.ByteArrayStream;

public class CompressMessage {
    /**
     * The callback provides information about archive items
     */
    private static final class MyCreateCallback //
            implements IOutCreateCallback<IOutItemZip> {
        private final byte[] /*f*/bytesToCompress/**/;

        private MyCreateCallback(byte[] bytesToCompress) {
            this./*f*/bytesToCompress/* */= bytesToCompress;
        }

        public void setOperationResult(boolean operationResultOk)//
                throws SevenZipException {
            // Handle result here
        }

        public void setTotal(long total) throws SevenZipException {
            // Track operation progress here
        }

        public void setCompleted(long complete) throws SevenZipException {
            // Track operation progress here
        }

        public IOutItemZip getItemInformation(int index,//
                OutItemFactory<IOutItemZip> outItemFactory) {
            IOutItemZip outItem = outItemFactory.createOutItem();

            // Convert the message into the sequential byte stream
            outItem.setDataStream(new ByteArrayStream(/*f*/bytesToCompress/**/, true));
            outItem.setDataSize((long) /*f*/bytesToCompress/**/./*f*/length/**/);

            // Set name of the file in the archive
            outItem.setPropertyPath("message.txt");
            outItem.setPropertyCreationTime(new Date());

            // To get u+rw permissions on linux, if extracting with unzip
            // outItem.setPropertyAttributes(Integer.valueOf(0x81808000));

            return outItem;
        }

        public void freeResources(int index, IOutItemZip outItem) {
            // no need to close ByteArrayStream
        }
    }

    public static void main(String[] args) {
        if (args./*f*/length/* */!= 2) {
            System.out.println("Usage: java CompressMessage <archive> <msg>");
            return;
        }

        final byte[] bytesToCompress = args[1].getBytes();

        RandomAccessFile raf = null;
        IOutCreateArchiveZip outArchive = null;
        try {
            raf = new RandomAccessFile(args[0], "rw");

            outArchive = SevenZip.openOutArchiveZip();
            outArchive.setLevel(5);
            outArchive.createArchive(new RandomAccessFileOutStream(raf), 1, //
                    new MyCreateCallback(bytesToCompress));

            System.out.println("Compression operation succeeded");
        } catch (SevenZipException e) {
            System.err.println("7z-Error occurs:");
            // Get more information using extended method
            e.printStackTraceExtended();
        } catch (Exception e) {
            System.err.println("Error occurs: " + e);
        } finally {
            if (outArchive != null) {
                try {
                    outArchive.close();
                } catch (IOException e) {
                    System.err.println("Error closing archive: " + e);
                }
            }
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    System.err.println("Error closing file: " + e);
                }
            }
        }
    }
}
/* END_SNIPPET */