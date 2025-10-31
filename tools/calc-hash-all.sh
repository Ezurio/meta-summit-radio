#! /bin/bash

set -e

[ -z "${1}" ] && { echo "Usage: ${0} <version>"; exit 1; } 

dir=$(dirname "${0}")
ver=${1}

# shellcheck disable=SC2034
file="radio-stack-hashes.inc"

# shellcheck source=/dev/null
. "${dir}/calc-common.sh"

# Common files

files="\
  summit_supplicant/laird/${ver}/summit_supplicant-src-${ver}.tar.gz \
  backports/laird/${ver}/summit-backports-${ver}.tar.bz2 \
  adaptive_bt/src/${ver}/adaptive_bt-src-${ver}.tar.gz \
  lrd-network-manager/src/${ver}/summit-network-manager-src-${ver}.tar.xz \
"

# 60 specific files

files="${files} firmware/${ver}/summit-som8mp-radio-firmware-${ver}.tar.bz2"


for i in x86 x86_64 arm-eabi arm-eabihf aarch64 powerpc64-e5500
do
  files="${files} \
    adaptive_ww/laird/${ver}/adaptive_ww-${i}-${ver}.tar.bz2 \
    summit_supplicant/laird/${ver}/summit_supplicant_libs-${i}-${ver}.tar.bz2 \
    mfg60n/laird/${ver}/mfg60n-${i}-${ver}.tar.bz2 \
  "
done

for i in pcie-uart pcie-usb sdio-uart sdio-sdio usb-usb usb-uart
do
  files="${files} firmware/${ver}/summit-60-radio-firmware-${i}-${ver}.tar.bz2"
done

# LWB-IF specific files

files="${files} \
  firmware/${ver}/summit-lwb-firmware-${ver}.tar.bz2 \
  firmware/${ver}/summit-lwbplus-firmware-${ver}.tar.bz2 \
"
for i in sdio-div sdio-sa sdio-sa-m2 usb-div usb-sa usb-sa-m2
do
  files="${files} firmware/${ver}/summit-lwb5plus-${i}-firmware-${ver}.tar.bz2"
done
for i in sdio pcie
do
  files="${files} firmware/${ver}/summit-if573-${i}-firmware-${ver}.tar.bz2"
done
for i in sdio-div sdio-sa
do
  files="${files} firmware/${ver}/summit-if513-${i}-firmware-${ver}.tar.bz2"
done

for i in arm-eabi arm-eabihf aarch64
do
  files="${files} regCypress/laird/${ver}/regCypress-${i}-${ver}.tar.bz2"
done

for i in x86 x86_64 arm-eabi arm-eabihf aarch64 powerpc64-e5500
do
  files="${files} \
    regLWB5plus/laird/${ver}/regLWB5plus-${i}-${ver}.tar.bz2 \
    regLWBplus/laird/${ver}/regLWBplus-${i}-${ver}.tar.bz2 \
    regIF573/laird/${ver}/regIF573-${i}-${ver}.tar.bz2 \
    regIF513/laird/${ver}/regIF513-${i}-${ver}.tar.bz2 \
  "
done

# NX61x specific files

files="${files} \
  firmware/${ver}/summit-nx61x-firmware-${ver}.tar.bz2 \
  firmware/${ver}/summit-nx61x-1218-firmware-${ver}.tar.bz2 \
"

for i in x86 x86_64 arm-eabi arm-eabihf aarch64 powerpc64-e5500
do
  files="${files} mfg611/laird/${ver}/mfg611-${i}-${ver}.tar.bz2"
done

# TI specific files

for i in WW US JP EU CA AU
do
  files="${files} firmware/${ver}/summit-ti351-${i}-firmware-${ver}.tar.bz2"
done

for i in x86 x86_64 arm-eabi arm-eabihf aarch64 powerpc64-e5500
do
  files="${files} regTI351/laird/${ver}/regTI351-${i}-${ver}.tar.bz2"
done

# BDSDMAC specific files

files="${files} firmware/${ver}/summit-bdsdmac-firmware-${ver}.tar.bz2"

# MSD specific files

files="${files} \
  firmware/${ver}/summit-ath6k-6003-firmware-${ver}.tar.bz2 \
  firmware/${ver}/summit-ath6k-6004-firmware-${ver}.tar.bz2 \
"

for i in arm-eabi arm-eabihf
do
  files="${files} \
    summit_supplicant/laird/${ver}/summit_supplicant_libs_legacy-${i}-${ver}.tar.bz2 \
    reg45n/laird/${ver}/reg45n-${i}-${ver}.tar.bz2 \
    reg50n/laird/${ver}/reg50n-${i}-${ver}.tar.bz2 \
    "
done

calc_hash sha256sum "${files}"
calc_hash md5sum "${files}"

mkdir -p tmp-bin

for i in sdio usb; do
  scp ${cmdopt}:${prefix}/firmware/${ver}/summit-lwb5plus-${i}-div-firmware-${ver}.tar.bz2 ./tmp-bin
  tar -xjf ./tmp-bin/summit-lwb5plus-${i}-div-firmware-${ver}.tar.bz2 -C ./tmp-bin

  scp ${cmdopt}:${prefix}/firmware/${ver}/summit-lwb5plus-${i}-sa-firmware-${ver}.tar.bz2 ./tmp-bin
  tar -xjf ./tmp-bin/summit-lwb5plus-${i}-sa-firmware-${ver}.tar.bz2 -C ./tmp-bin
done

echo >> "${file}"
ls tmp-bin/lib/firmware/brcm/* | sed -n \
  -e 's/.*sdio-div_v\(.*\)\.hcd/LWB_PLUS_SDIO_HCD_VER = "\1"/p' \
  -e 's/.*sdio-sa_v\(.*\)\.hcd/LWB_PLUS_SDIO_SA_HCD_VER = "\1"/p' \
  -e 's/.*usb-div_v\(.*\)\.hcd/LWB_PLUS_USB_HCD_VER = "\1"/p' \
  -e 's/.*usb-sa_v\(.*\)\.hcd/LWB_PLUS_USB_SA_HCD_VER = "\1"/p' \
  -e 's/.*sdio-prod_v\(.*\)\.bin/LWB_PLUS_SDIO_BIN_VER = "\1"/p' \
  -e 's/.*usb-div-prod_v\(.*\)\.bin/LWB_PLUS_USB_BIN_VER = "\1"/p' >> "${file}"

rm -rf tmp-bin
