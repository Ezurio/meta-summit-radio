SECTION = "kernel"

LICENSE = "GPL-2.0-only & Ezurio"
NO_GENERIC_LICENSE[Ezurio] = "LICENSE.ezurio"
LIC_FILES_CHKSUM = "\
    file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46 \
    file://LICENSE.ezurio;md5=fd3dd0630b215465b6f50540642d5b93 \
    "

PV = "${RADIO_VERSION}"

inherit module cml1

DEPENDS += "coreutils-native bison-native flex-native kern-tools-native"

S = "${SUMMIT_UNPACKDIR}/summit-backports-${PV}"

SUMMIT_URI ?= "${SUMMIT_URI_BASE}"
SUMMIT_URI:summit-internal = "${SUMMIT_URI_BASE}/backports/laird/${PV}"

SRC_URI += "${SUMMIT_URI}/summit-backports-${PV}.tar.bz2;name=summit-backports"

EXTRA_OEMAKE = "\
    KLIB=${STAGING_KERNEL_DIR} \
    KLIB_BUILD=${STAGING_KERNEL_BUILDDIR} \
    "

def package_suffix(d):
    bpn = d.getVar("BPN", True)
    prefix = 'kernel-module'
    if bpn.startswith(prefix):
        return bpn[len(prefix):]
    return '-backports'

KERNEL_MODULE_PACKAGE_SUFFIX_OLD = "-${KERNEL_VERSION}"
KERNEL_MODULE_PACKAGE_SUFFIX = "${@package_suffix(d)}-${KERNEL_VERSION}"

python fixup_rdepends () {
    import oe.utils
    packages = oe.utils.packages_filter_out_system(d)
    suffix = d.getVar('KERNEL_MODULE_PACKAGE_SUFFIX')
    suffix_old = d.getVar('KERNEL_MODULE_PACKAGE_SUFFIX_OLD')
    for p in packages:
        rdep = d.getVar('RDEPENDS:' + p)
        if not rdep:
            continue

        update = False
        rlist = rdep.split()
        for i, r in enumerate(rlist):
            if not r in packages:
                rlist[i] = r.replace(suffix, suffix_old)
                update = True
        if update:
            d.setVar('RDEPENDS:' + p, ' '.join(rlist))
}

PACKAGESPLITFUNCS:append = " fixup_rdepends"

KCONFIG_CONFIG_COMMAND = "${EXTRA_OEMAKE} CC=${BUILD_CC} menuconfig"

# Reconfiguration must be triggered on kernel config change through
# defconfig or menuconfig, menuconfig does not have it's own stamp
# and runs after do_configure, so use compile instead
do_configure[depends] += "virtual/kernel:do_compile"

do_configure () {
    rm -f "${S}/.kernel_config_md5"

    # Copy defconfig to .config if .config does not exist. This allows
    # recipes to manage the .config themselves in do_configure:prepend().
    if [ -f "${SUMMIT_UNPACKDIR}/defconfig" ] && [ ! -f "${S}/.config" ]; then
        cp "${SUMMIT_UNPACKDIR}/defconfig" "${S}/.config"
    fi

    # Copy in-tree defconfig to .config if .config does not exist.
    if [ ! -f "${S}/.config" ]; then
        cp "${S}/defconfigs/${BACKPORTS_CONFIG}" "${S}/.config"
    fi

    "${S}/kconf/merge_config.sh" -O "${S}" -m "${S}/.config" ${@" ".join(find_cfgs(d))}

    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    oe_runmake CC="${BUILD_CC}" olddefconfig
}

do_savedefconfig () {
    bbplain "Saving defconfig to:\n${S}/defconfig"
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
    oe_runmake CC="${BUILD_CC}" savedefconfig
}

do_savedefconfig[nostamp] = "1"
addtask savedefconfig after do_configure

do_install:append () {
    install -d "${D}${includedir}/${BPN}"
    cp -R --no-dereference --preserve=mode,links -v "${S}/include" "${D}${includedir}/${BPN}"
    cp -R --no-dereference --preserve=mode,links -v "${S}/backport-include" "${D}${includedir}/${BPN}"
    rm -f "${D}${includedir}/${BPN}/backport-include/linux/module.h"
}
