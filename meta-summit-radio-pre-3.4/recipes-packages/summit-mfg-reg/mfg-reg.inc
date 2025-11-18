SECTION = "Wireless"

LICENSE = "Ezurio"
NO_GENERIC_LICENSE[Ezurio] = "LICENSE.ezurio"
LIC_FILES_CHKSUM = "file://LICENSE.ezurio;md5=fd3dd0630b215465b6f50540642d5b93"

PV = "${RADIO_VERSION}"

HFP = "${@bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', 'eabihf', 'eabi', d)}"

REG_ARCH_x86 = "-x86"
REG_ARCH_x86-64 = "-x86_64"
REG_ARCH_powerpc64 = "-powerpc-e5500"
REG_ARCH_arm = "-arm-${HFP}"
REG_ARCH_aarch64 = "-aarch64"

REG_FULL_NAME = "${REG_NAME}${REG_ARCH}-${PV}"

SUMMIT_URI ?= "${SUMMIT_URI_BASE}"
SUMMIT_URI_summit-internal = "${SUMMIT_URI_BASE}/${REG_NAME}/laird/${PV}"

SRC_URI = "${SUMMIT_URI}/${REG_FULL_NAME}.tar.bz2;subdir=src;name=${REG_NAME}${REG_ARCH}"

S = "${WORKDIR}/src"
#B = "${WORKDIR}/build"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

FILES_${PN} += "${nonarch_base_libdir}/*"

INSANE_SKIP_${PN} = "ldflags already-stripped"

ALLOW_EMPTY_${PN}-dev = "0"
ALLOW_EMPTY_${PN}-dbg = "0"

RDEPENDS_${PN} += " \
	libedit libnl libnl-genl libnl-route \
	${@bb.utils.contains('DISTRO_FEATURES', 'bluetooth', 'bluez5', '', d)} \
	"

python do_unpack_append() {
    import tarfile, os
    mfgname = d.expand(d.getVar("REG_FULL_NAME", True))
    s = d.expand(d.getVar("S", True))
    #b = d.expand(d.getVar("B", True))
    os.system("cd " + s + " && ./" + mfgname + ".sh tar")
    tf = tarfile.open(s + "/" + mfgname + ".tar.bz2")
    tf.extractall(s)
}

INSTALL_TOOLS = "lru_* lmu_* \
    ${@bb.utils.contains('DISTRO_FEATURES', 'bluetooth', 'btlru_*', '', d)}"

do_install() {
    for i in ${INSTALL_TOOLS}; do
        if [ -f "${B}/${i}" ]; then
            install -D -m 755 -t "${D}${bindir}" "${B}/${i}"
        fi
    done
}
