#!/bin/bash
echo "" > "`dirname $0`/$1/src/main/resources/db/migration/V`date +%s`__$2.sql"