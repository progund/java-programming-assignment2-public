#!/bin/bash

LOG_FILE=ab.log
ECHO=echo
MC=org.addressbook.main.SimpleMain

AWK=awk

declare -a GIVEN_NAMES
declare -a FAMILY_NAMES

GIVEN_NAMES=(Adney Aldo Aleyn Alford Amherst Angel Anson Archibald Aries Arwen Astin Atley Atwell Audie Avery Ayers Baker Balder Ballentine Bardalph Barker Barric Bayard Bishop Blaan Blackburn Blade Blaine Blaze Bramwell Brant Brawley Breri Briar Brighton Broderick Bronson Bryce Burdette Burle Byrd Byron Cabal Cage Cahir Cavalon Cedar Chatillon Churchill Clachas Girls Addison Alivia Allaya Amarie Amaris Annabeth Annalynn Araminta Ardys Ashland Avery Bedegrayne Bernadette Billie Birdee Bliss Brice Brittany Bryony Cameo Carol Chalee Christy Corky Cotovatre Courage Daelen Dana Darnell Dawn Delsie Denita Devon Devona Diamond Divinity Duff Dustin Dusty Ellen Eppie Evelyn Everilda Falynn Fanny Faren Freedom Gala Galen Gardenia Pagination Current)

FAMILY_NAMES=(Smith Jones Brown Johnson Williams Miller Taylor Wilson Davis White Clark Hall Thomas Thompson Moore Hill Walker Anderson Wright Martin Wood Allen Robinson Lewis Scott Young Jackson Adams Tryniski Green Evans King Baker John Harris Roberts Campbell James Stewart Lee County Turner Parker Cook Mc Edwards Morris Mitchell Bell Ward Watson Morgan Davies Cooper Phillips Rogers Gray Hughes Harrison Carter Murphy Collins Henry Foster Richardson Russell Hamilton Shaw Bennett Howard Reed Fisher Marshall May Church Washington Kelly Price Murray William Palmer Stevens Cox Robertson Miss Clarke Bailey George Nelson Mason Butler Mills Hunt Island Simpson Graham Henderson Ross Stone Porter Wallace Kennedy Gibson West Brooks Ellis Barnes Johnston Sullivan Wells Hart Ford Reynolds Alexander Co Cole Fox Holmes Day Chapman Powell Webster Long Richards Grant Hunter Webb Thomson Wm Lincoln Gordon Wheeler Street Perry Black Lane Gardner City Lawrence Andrews Warren Spencer Rice Jenkins Knight Armstrong Burns Barker Dunn Reid College Mary Hayes Page Rose Patterson Ann Crawford Arnold House)


# sleep interval
INT=0.3
ADDS=10

# internal
FAILS=0
REPORT=report.log
rm -f $REPORT

usage()
{
    echo "NAME"
    echo "  $(basename $0)"
    echo
    echo "SYNOPSIS"
    echo "  $(basename $0) [OPTION]"
    echo
    echo "DESCRIPTION"
    echo "  Script to automate tests of the Address Book assignment as found in:"
    echo "  http://wiki.juneday.se/mediawiki/index.php/Java:Assignment_-_Address_book"
    echo
    echo "  Options"
    echo "  --log-file FILE     "
    echo "         output logs to FILE. Default \"ab.log\""
    echo
    echo "  --main-class NAME     "
    echo "         use NAME as class to start. Default \"$MC\""
    echo
    echo "  --contacts AMOUNT     "
    echo "         add AMOUNT of Contacts. Default \"$ADDS\""
    echo
    echo "  --help, -h"
    echo "         prints this helpt text"
    echo ""
}

AB="java -cp bin/ $MC"

parse_args()
{
    while [ "$1" != "" ]
    do
        case "$1" in
            "--log-file")
                LOG_FILE=$2
                shift
                ;;
            "--main-class")
                AB=$2
                shift
                ;;
            "--contacts")
                ADDS=$2
                shift
                ;;
            "--help" | "-h")
                usage
                exit 0
                ;;
            *)
                echo "SYNTAX ERROR: $1"
                echo
                usage
                exit 1
        esac
        shift
    done
}

GRADE_FILE=$(pwd)/grade.txt

set_menu_choices()
{
    LIST=0
    ADD=1
    EXIT=2

    TMP_FILE=.check-menu.txt
    echo -e "2\n" | $AB > $TMP_FILE 2>/dev/null
    RET=$?
    if [ "$RET" -ne 0 ]
    then
        echo "Could not start the program using the class: $AB"
        echo 
        echo "If you would like to run this script using another class (as main) try:"
        echo "  $0 --main-class <package.class>"
        echo "where package.class should be replaced by your class' complete name"
        echo 
        exit 1
    fi
       
    LIST=$(grep -i "^[0-9]*[ ]*list" $TMP_FILE | head -1 | $AWK '{ print $1}')
    ADD=$(grep -i "^[0-9]*[ ]*add" $TMP_FILE | head -1 | $AWK '{ print $1}')
    REMOVE=$(grep -i "^[0-9]*[ ]*" $TMP_FILE | egrep -i "delete|remove" | head -1 | $AWK '{ print $1}')
    EXIT=$(grep -i "^[0-9]*[ ]*" $TMP_FILE | egrep -i "exit|quit" | head -1 | $AWK '{ print $1}')
}

log()
{
    echo "$*" >> $LOG_FILE
}

logn()
{
    echo -n "$*" >> $LOG_FILE
}

elog()
{
    echo "$*" 
    log "$*" 
}

elogresult()
{
    if [ $1 -ne 0 ]
    then
        ((FAILS++))
        echo " FAIL ($1)" 
        log " FAIL ($1)"
    else
        echo " OK"
        log " OK"
    fi
}

elogexpt()
{
    EXP=$1
    REC=$2
    if [ $EXP -ne $REC ]
    then
        ((FAILS++))
        echo " FAIL (expected: $EXP got: $REC)" 
        log " FAIL (expected: $EXP got: $REC)"
        return 1
    else
        echo " OK"
        log " OK"
        return 0
    fi
}

elognexpt()
{
    NEXP=$1
    REC=$2

    if [ $NEXP -eq $REC ]
    then
        ((FAILS++))
        echo " FAIL (not expected: $NEXP got: $REC)" 
        log " FAIL (not expected: $NEXP got: $REC)"
        return 1
    else
        echo " OK"
        log " OK"
        return 0
    fi
}

elogn()
{
    echo -n "$*" 
    logn "$*" 
}

compile()
{
    elogn " * Compiling:"
    JAVA_FILES=$(ls   src/org/addressbook/tests/*.java src/org/addressbook/storage/*.java src/org/addressbook/main/*.java src/org/addressbook/ui/cli/menu/*.java src/org/addressbook/textutils/*.java 2>/dev/null | tr '[\n]' '[ ]')
    javac -cp src/ -d bin/ -Xlint:unchecked $JAVA_FILES >> $LOG_FILE 2>> $LOG_FILE
    RET=$?
    elogresult $RET
    echo " * Compiled files: $JAVA_FILES" 
    
    return $RET
}

test_exit()
{
    elogn " * Testing exit:"
    # Simple test: check if we can find the menu
    NR=$($ECHO "$EXIT" | $AB | grep -c "===Addre" )
    elogexpt 1 $NR
    return $?
}


new_contact()
{
    GNAME=${GIVEN_NAMES[$(expr $RANDOM % ${#GIVEN_NAMES[@]})]}
    FNAME=${FAMILY_NAMES[$(expr $RANDOM % ${#FAMILY_NAMES[@]})]}
    NAME="$GNAME $FNAME"
    EMAIL=$(echo "${GNAME}"@"${FNAME}".com | tr '[A-Z]' '[a-z]')
    PHONE=$RANDOM$RANDOM
} 



add_single()
{
    printf "${ADD}\n"
    #sleep $INT
    printf "$GNAME $FNAME\n"
    #sleep $INT
    printf "$EMAIL\n"
    #sleep $INT
    printf "$PHONE\n"
    #sleep $INT
    printf "${EXIT}\n"
    #sleep $INT
} 

add_multiple()
{
    printf "${ADD}\n"
    #sleep $INT
    printf "$GNAME $FNAME\n"
    #sleep $INT
    printf "$EMAIL\n"
    #sleep $INT
    printf "$PHONE\n"
    #sleep $INT
} 

test_add_single()
{
    new_contact
    elogn "   * [$CONTACT_NUMBER/$ADDS] $NAME ($EMAIL / $PHONE):  "
    add_single | $AB >> $LOG_FILE
    NR=$($ECHO "$LIST" | $AB 2>/dev/null | grep -c "$NAME" )
    elognexpt 0 $NR

    # In list?
    elogn "   * stored:  "
    NR=$($ECHO "$LIST" | $AB 2>/dev/null | grep -c "$NAME" )
    elognexpt 0 $NR

    return $?
}

test_add_multiple_helper()
{
    for CONTACT_COUNT in $(seq 1 $MULT_ADDS)
    do
        new_contact
        add_multiple
    done
    printf "${EXIT}\n"
}

test_add_multiple()
{
    BEFORE=$($ECHO "$LIST" | $AB 2>/dev/null | wc -l )
    test_add_multiple_helper | $AB >> $LOG_FILE
    NR=$($ECHO "$LIST" | $AB 2>/dev/null | grep -c "$NAME" )
    AFTER=$($ECHO "$LIST" | $AB 2>/dev/null | wc -l )
#    echo
 #   echo " AFTER:  $AFTER"
  #  echo " BEFORE: $BEFORE"
   # echo " LIMIT:  $LIMIT"
    #echo " ADDS:   $ADDS"
    elogexpt $(( $AFTER - $BEFORE)) $MULT_ADDS

    return 
}

pushd_src_dir()
{
    SRC_DIR=$(find . -type d -name src | grep -v .DS_STORE | grep -v __MACOS | head -1)
    if [ "$SRC_DIR" = "/" ] || [ "$SRC_DIR" = "" ]
    then
        echo "SRC_DIR $SRC_DIR faulty"
        echo -n "pwd: "
        return 1
    fi
    pushd "${SRC_DIR}"/.. > /dev/null
    return $?
#    elog "Working src dir: $(pwd)"
}

pushd_dir()
{
    DIR=$1
    pushd "${DIR}" > /dev/null
#    elog "Working dir: $(pwd)"
}

popd_dir()
{
    popd > /dev/null
}



check_student()
{
    rm -f ~/.address_book
    compile
    COMP=$?
    set_menu_choices

    #    echo "FAILS: $FAILS"
    if [ $COMP -eq 0 ]
    then
        test_exit
        elog " * Test adding $ADDS contacts one by one (add, list and quit): "
        LIMIT=$(( $ADDS - 1))
        for CONTACT_NUMBER in $(seq 1 $ADDS)
        do
            test_add_single
        done
        elogn " * Test adding multiple contacts ($MULT_ADDS contacts): "
        test_add_multiple
    else
        ((FAILS++))
    fi

    elog ""
}

check_and_execute_script()
{
    if [ ! -f $1 ]
    then
        echo "Can't find script: $1"
        exit 1
    fi
    chmod a+x $1
    printf " * %-35s: " $1
    ./$1 >> $LOG_FILE

    RET=$?
    if [ $RET -ne 0 ]
    then
        echo "Failed tests in script: $1"
        echo "Returned with error code: $RET"
        echo " "
        echo "Check the log file: ab.log"
        exit $RET
    fi
    echo " OK"
}

setup_os()
{
    if [ "$(uname  | grep -ic cygwin)" != "0" ]
    then
        AWK=gawk
        echo " --== Notification  ==--"
        echo "   -- You seem to be using Cygwin Windows"
        echo "   -- Due to some default settings in Windows"
        echo "   -- we're reducing the number of contacts to add"
        ADDS=2
        MULT_ADDS=$(( $ADDS * 2 ))
    fi
}


MULT_ADDS=$(( $ADDS * 100 ))
setup_os

parse_args $*
echo "Using log file: $LOG_FILE"



#
# Test with existing scripts
#
echo "Executing old test scripts"
check_and_execute_script clean.sh  
check_and_execute_script build.sh
check_and_execute_script run_test_contact.sh  
check_and_execute_script run_test_simple_address_book.sh 

echo "Starting tests...."
check_student .
echo "Tests performed...."
echo "Creating report...."

report()
{
    echo "$*"
    echo "$*" >> $REPORT
}

echo
echo
echo
echo
report "*********************************"
report "********* Test report ***********" 
report "*********************************"
report " * Date:               $(date)"
report " * User:               $(whoami)"
report " * OS info:            $(uname -a)"
report " * Pwd:                $(pwd)"
report " * Java:               $(java -version 2>&1 | tr '[\n]' '[ | ]')"
report " * Bash:               $(bash --version | head -1)"
report " * Main class:         $MC"
report " * Start command line: $AB"
report " * Program commands:"
report " *   list:   $LIST"
report " *   add:    $ADD"
report " *   remove: $REMOVE"
report " *   exit:   $EXIT"
report " * Useful command lines:"
report " *   list contacts:      "
report "        printf \"$LIST\n$EXIT\n\" | $AB "
report " * Added single:      $ADDS contacts"
report " * Added multiple:    $MULT_ADDS contacts"
report "*********************************"
if [ $FAILS -ne 0 ]
then
    report " *"
    report " * Hmm, some strange things were found"
    report " * Number of encountered (assumed) failures: $FAILS"
    report " *"
#    report " * Try modifying this script ($0), by:"
#    report " * 1. Uncomment the sleep statements, e g change "
#    report "     #sleep $INT"
#    report " * to"
#    report "     sleep $INT"
#    report " * 2. AND increase the value of INT in this script, e g "
#    report " *   INT=1"
    report " *"
    exit 1
else
    report " *"
    report " * All is well..."
    report " *"
fi
report "*********************************"
echo 
echo
echo "Packing yer files into a zip file"
rm -r assignment-*-auto.zip
ZIP_FILE=assignment-cb-auto.zip
zip --quiet -r $ZIP_FILE $JAVA_FILES $REPORT $LOG_FILE >/dev/null
echo " * created: $ZIP_FILE"
echo " * .... which you can send in to teacher(s) if that is requested"
echo " * .. and to check the content of this zip file, do:"
echo " unzip -l $ZIP_FILE"
