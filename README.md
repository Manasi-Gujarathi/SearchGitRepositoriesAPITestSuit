# SearchGitRepositoriesAPITestSuit
This is Java based automation test project to test functionality and behavior of the following GitHub API endpoint which covers test cases for querying based on a series of keywords as well as ordering and sorting of returned results.

**Application under test** : https://api.github.com/search/repositories? (Search GitHub repository endopoint)

**Documentation for endpoint** : https://developer.github.com/v3/search/#search-repositories

**Functionality covered**: From identified test cases, I have chosen to automate following test cases,

- TC <1> User can search repositories where fork count is greater than given number and sort result by most stared repositories in ascending order.
- TC <2> User can search repositories with given keyword in their name and sort result by updated time in descending order.
- TC <3> User can search repositories with given username in login which are created after specified date.
- TC <4> User can search repositories for multiple languages and sort result in descending order of their fork count.
- TC <5> User can search repositories owned by GitHUB and which are archived and sort them in ascending order of their stars.

 1.  I have used Cucumber to write test scenarios in gherkin language and used Java custome code to perform API operations. 
 2.  This Maven project is configured to accept environment parameters such that it can be run on different environments on CI/CD  pipeline. Detail commands to use this test suite is given in below document
 3.  This Maven project is configured to accept maxcount parameter such that user can specify maximum number of records for the query result. I have kept 5 as default value if no count is provided. (query parameter ?page='maxcount' is used for this.)
 4.  Note: Maven command to run project is mentioned below.


**Project  setup**:

--**Required tools**:

|Category                            |Tools                    |Version       |
|------------------------------------|-------------------------|--------------|
|Programming language                |Java                     |8             |
|Build/ dependency management tool   |Maven                    |3.6.2         |
|IDE                                 |Eclipse                  |              |

-- **Steps to configure test suit:**

1.	Install Java 1.8 as per the instructions mentioned in this link
2.	Install Maven 3.6.2 or above locally as per the instructions provided in this link
3.	Install/ update maven dependencies through command prompt or eclipse IDE using "mvn install" command.
4.	Run the project using one of the following methods.
    1. In command prompt on the root folder of the project run below commmand.
test verify -Denv=prod -Dmaxcount=5
    2. In eclipse IDE use run as-> maven test and give below command.
test verify -Denv=prod -Dmaxcount=3
        1.	env: This property will define in which environment the script is getting executed. User can specify name of property file present in project directory. If no value is specified for environment project is configured to run test cases on production environment by default for this assignment.
        2.	Maxcount: Search results provides large number of records. For testing purpose I have provided this property where you can specify a maximum number of results. By default, 5 records will be analyzed if count is not provided. 
        3.  envPropFileLocation: If needed external property file can also be provided to run the project using this parameter
5.	This should run the project and generate reports using Cucumber

			

**Reports location:**		*/ServiceNow/target/cucumber-html-reports/overview-features.html*
