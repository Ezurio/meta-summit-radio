#! /bin/sh

set -e

: "${file:-?}"

[ -z "${ver}" ] && { echo "Usage: ${0} <version>"; exit 1; }

wget="/usr/bin/wget -T4 -t1 -P ${tmpdir:-.}"

[ -z "${RFPROS_FILESHARE_USER}" ] || \
	wget="${wget} --user=${RFPROS_FILESHARE_USER} --password=${RFPROS_FILESHARE_PASS} --auth-no-challenge"

prefix="https://files.devops.rfpros.com/builds/linux"

calc_file () {
  ${wget} "${prefix}/${1}/${ver}/${2}" || exit 1
  echo "SRC_URI[${3}.md5sum] = \"$(md5sum "${2}" | awk '{print $1}')\"" >> "${file}"
  echo "SRC_URI[${3}.sha256sum] = \"$(sha256sum "${2}" | awk '{print $1}')\"" >> "${file}"
  rm -f "${2}"
}

printf 'RADIO_VERSION = "%s"\n\n' "${ver}" > "${file}"
