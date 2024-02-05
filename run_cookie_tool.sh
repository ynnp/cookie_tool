#!/bin/bash

# Set the path to your Java executable
java_executable="java"

# Set the path to your JAR file
jar_file="cookie_tool.jar"

# Check if Java is installed
if ! command -v "$java_executable" &> /dev/null; then
    echo "Error: Java is not installed or not in the PATH."
    exit 1
fi

# Check if the JAR file exists
if [ ! -f "$jar_file" ]; then
    echo "Error: JAR file '$jar_file' not found."
    exit 1
fi

# Function to run the command and print it
run_command() {
    local csv_file="$1"
    local date_parameter="$2"

    echo "Running command: $java_executable -jar $jar_file -f $csv_file -d $date_parameter"
    "$java_executable" -jar "$jar_file" -f "$csv_file" -d "$date_parameter"

    # Capture the exit code of the Java process
    local exit_code=$?

    # Print a separator line
    echo "------------------------"

    # Return the exit code
    return $exit_code
}

# Run the command with different arguments
run_command "cookie_log.csv" "2018-12-09"
run_command "cookie_log.csv" "2018-12-08"
run_command "cookie_log.csv" "2018-12-07"
run_command "cookie_log.csv" "2018-12-41"
run_command "cookie_log.csv" "2018-12-23"
run_command "cookie_log.csvr" "2018-12-23"
run_command "cookie_logtwe.csv" "2018-12-23"