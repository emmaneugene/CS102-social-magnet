FILE=.env
if [ ! -f "$FILE" ]; then
  RED='\033[0;31m'
  NOCOLOR='\033[0m'
  echo -e "\n${RED}File not found error: Please include config file (.env) in the project's root directory. See file .env-sample for reference${NOCOLOR}\n"
  exit 1
fi

mvn compile;
