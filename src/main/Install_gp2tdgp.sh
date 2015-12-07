
SCRIPT_DIR=`dirname $0`
PROCESS_ARGS="$@"
ERROR_CODE=0

LOAD_DATE=$(date +"%Y-%m-%d")
LOAD_DATE_TIME=$(date +"%Y-%m-%d_%H_%M_%S")




#Check that we have 2 parameter
if (( "$#" != 1 )); then
	_LOG "Usage: "
	_LOG "	Install_gp2tdgp.sh jar_folder executable_folder"
	exit 1
fi

mkdir $1
ERROR_CODE="$?"
if (( $ERROR_CODE == 0 )); then
      _LOG "Creating jar folder"
fi


mkdir $2
ERROR_CODE="$?"
if (( $ERROR_CODE == 0 )); then
      _LOG "Creating exec folder"
fi
cd $2


ERROR_CODE="$?"
if (( $ERROR_CODE == 0 )); then
      _LOG "Downloading last version"
      wget http://jenkins-csoemea.cfapps.io/job/gptd2td/lastSuccessfulBuild/artifact/target/gptdtgp-0.1.jar
fi



ERROR_CODE="$?"
if (( $ERROR_CODE == 0 )); then
      _LOG "uncompress jar"
      jar xf gptdtgp-0.1.jar
fi

ERROR_CODE="$?"
if (( $ERROR_CODE == 0 )); then
      _LOG "copying  jars"
      cp src/jar/* $1
fi

ERROR_CODE="$?"
if (( $ERROR_CODE == 0 )); then
      _LOG "Finish process jar"
		echo "your classpath in the external table should be $1/terajdbc4.jar:$1/tdgssconfig.jar:$2 gptd2gp"
		echo "see src/sql/example.sql"
rm gptdtgp-0.1.jar
fi

exit $ERROR_CODE 



