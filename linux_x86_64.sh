#!/bin/bash
##################################
# InSilico Start Script
##################################
echo "Starting insilico"

# directory of this script 
ROOTDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
cd $ROOTDIR/releng/org.draegerlab.insilico.product/target/products/insilico/linux/gtk/x86_64/insilico/
./InSilico
cd $ROOTDIR

