#! /bin/sh

: "${file:-?}"

[ -z "${ver}" ] && { echo "Usage: ${0} <version>"; exit 1; }

wget="/usr/bin/wget -T4 -t1 -P ${tmpdir:-.}"

[ -z "${RFPROS_FILESHARE_USER}" ] || \
	wget="${wget} --user=${RFPROS_FILESHARE_USER} --password=${RFPROS_FILESHARE_PASS} --auth-no-challenge"

prefix="https://files.devops.rfpros.com/builds/linux"

calc_file () {
  ${wget} "${prefix}/${1}/${ver}/${2}" || exit 1
  
  dlnd="${tmpdir:-.}/${2}"
  md5=$(md5sum "${dlnd}" | cut -d ' ' -f 1)
  sha256=$(sha256sum "${dlnd}" | cut -d ' ' -f 1)
  rm -f "${dlnd}"

  {
    echo "SRC_URI[${3}.md5sum] = \"${md5}\""
    echo "SRC_URI[${3}.sha256sum] = \"${sha256}\""
  }  >> "${file}"
}

printf 'RADIO_VERSION = "%s"\n\n' "${ver}" > "${file}"
