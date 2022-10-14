package org.callimard.easyfuse.nio;

import ru.serce.jnrfuse.flags.AccessConstants;
import ru.serce.jnrfuse.struct.FileStat;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.file.AccessMode;
import java.nio.file.attribute.PosixFilePermission;
import java.util.EnumSet;
import java.util.Set;

@Singleton
public class FileAttributesUtil {

    @Inject
    FileAttributesUtil() {
        // Nothing
    }

    public Set<AccessMode> accessModeMaskToSet(int mask) {
        Set<AccessMode> accessModes = EnumSet.noneOf(AccessMode.class);
        // @formatter:off
        if ((mask & AccessConstants.R_OK) == AccessConstants.R_OK) accessModes.add(AccessMode.READ);
        if ((mask & AccessConstants.W_OK) == AccessConstants.W_OK) accessModes.add(AccessMode.WRITE);
        if ((mask & AccessConstants.X_OK) == AccessConstants.X_OK) accessModes.add(AccessMode.EXECUTE);
        // @formatter:on
        return accessModes;
    }

    public Set<PosixFilePermission> octalModeToPosixPermissions(long mode) {
        Set<PosixFilePermission> result = EnumSet.noneOf(PosixFilePermission.class);
        // @formatter:off
        if ((mode & 0400) == 0400) result.add(PosixFilePermission.OWNER_READ);
        if ((mode & 0200) == 0200) result.add(PosixFilePermission.OWNER_WRITE);
        if ((mode & 0100) == 0100) result.add(PosixFilePermission.OWNER_EXECUTE);
        if ((mode & 0040) == 0040) result.add(PosixFilePermission.GROUP_READ);
        if ((mode & 0020) == 0020) result.add(PosixFilePermission.GROUP_WRITE);
        if ((mode & 0010) == 0010) result.add(PosixFilePermission.GROUP_EXECUTE);
        if ((mode & 0004) == 0004) result.add(PosixFilePermission.OTHERS_READ);
        if ((mode & 0002) == 0002) result.add(PosixFilePermission.OTHERS_WRITE);
        if ((mode & 0001) == 0001) result.add(PosixFilePermission.OTHERS_EXECUTE);
        // @formatter:on
        return result;
    }

    public long posixPermissionsToOctalMode(Set<PosixFilePermission> permissions) {
        long mode = 0;
        // @formatter:off
        if (permissions.contains(PosixFilePermission.OWNER_READ)) mode = mode | FileStat.S_IRUSR;
        if (permissions.contains(PosixFilePermission.GROUP_READ)) mode = mode | FileStat.S_IRGRP;
        if (permissions.contains(PosixFilePermission.OTHERS_READ)) mode = mode | FileStat.S_IROTH;
        if (permissions.contains(PosixFilePermission.OWNER_WRITE)) mode = mode | FileStat.S_IWUSR;
        if (permissions.contains(PosixFilePermission.GROUP_WRITE)) mode = mode | FileStat.S_IWGRP;
        if (permissions.contains(PosixFilePermission.OTHERS_WRITE)) mode = mode | FileStat.S_IWOTH;
        if (permissions.contains(PosixFilePermission.OWNER_EXECUTE)) mode = mode | FileStat.S_IXUSR;
        if (permissions.contains(PosixFilePermission.GROUP_EXECUTE)) mode = mode | FileStat.S_IXGRP;
        if (permissions.contains(PosixFilePermission.OTHERS_EXECUTE)) mode = mode | FileStat.S_IXOTH;
        // @formatter:on
        return mode;
    }

}