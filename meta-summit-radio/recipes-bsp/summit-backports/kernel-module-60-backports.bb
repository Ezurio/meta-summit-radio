SUMMARY = "Summit Backports for 60"

BACKPORTS_CONFIG = "${@bb.utils.contains('DISTRO_FEATURES','bluetooth','summit60','summit60_nbt',d)}"


RCONFLICTS:${PN} = " \
        kernel-module-lwb-if-backports \
        kernel-module-ti-backports \
        kernel-module-msd45-backports \
        kernel-module-msd50-backports \
        kernel-module-nx-backports \
        kernel-module-bdsdmac-backports \
        "

require summit-backports.inc
require radio-stack-version.inc
