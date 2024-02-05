CLI that allows to get the most active cookies for a specific day.

To return the result it **requires** two parameters:
1. Filename with cookie logs in csv format. Logs should contain cookie and timestamp separated by a comma.
2. Date for which the most active cookies will be searched. Date should be in yyyy-MM-dd format.

Usage:

`cookie -f {filename} -d {date}`

`cookie --file {filename} --date {date}`

For more convenient use, there is a bash script with set of input parameters. Just run:
`./run_cookie_tool.sh`