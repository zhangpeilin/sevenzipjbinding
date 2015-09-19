package net.sf.sevenzipjbinding.junit.compression;

import net.sf.sevenzipjbinding.ArchiveFormat;

/**
 * Tests compression, update and extraction of a single file using generic callback with 7z.
 * 
 * @author Boris Brodski
 * @version 9.20-2.00
 */
public class UpdateSingleFileGeneric7zTest extends UpdateSingleFileGenericAbstractTest {

    @Override
    protected ArchiveFormat getArchiveFormat() {
        return ArchiveFormat.SEVEN_ZIP;
    }

}