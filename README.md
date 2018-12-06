# WizTools.org JSON Validator

Command line tool to validate and format JSON files.

Mac users can use homebrew to install `json-validator`:

```
brew tap wiztools/repo
brew install json-validator
```

To run:

```
$ java -jar json-validator-fat-VERSION.jar -h
Usage: json-validator [options] [files]
When files are not given, STDIN is read for input.
Supported options are:
	--help     Display this help.
	--noout    Do not print formatted JSON to STDOUT.
	--noformat Do not format JSON.
	--gson     Use Gson instead of default Jackson parser.
```
