
*This project is meant as start code for projects and exercises given in Flow-1+2 (+3 using the security-branch) at http://cphbusiness.dk in the Study Program "AP degree in Computer Science"*

*Projects which are expected to use this start-code are projects that require all, or most of the following technologies:*
 - *JPA and REST*
- *Testing, including database test*
- *Testing, including tests of REST-API's*
- *CI and CONTINUOUS DELIVERY*

## Flow 2 week 1
In this week we begin using json errormessages. The folder errorhandling is added to the project containing: 
- ExceptionDTO
- GenericExceptionMapper
With that addition, error message will be converted to json when a rest endpoint is called. Even a 404.
Execute > git clone -b errorhandling git@github.com:dat3startcode/dat3-startcode.git to clone the version of the startcode with errorhandling

### Preconditions
*In order to use this code, you should have a local developer setup + a "matching" droplet on Digital Ocean as described in the 3. semester guidelines* 
# Getting Started

This document explains how to use this code (build, test and deploy), locally with maven, and remotely with maven controlled by Github actions
 - [How to use](https://docs.google.com/document/d/1rymrRWF3VVR7ujo3k3sSGD_27q73meGeiMYtmUtYt6c/edit?usp=sharing)