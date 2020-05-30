# ratings-data-repo-codepipeline
AWS Codepipeline for Ratings-Data microservice

When the code is commited to github, codebuild retrieves the source and builds it using mvn clean package and generates the latest jar file
The CodeBuild then uses the DockerFile to download java image and packages Java images with the springboot jar file to create a Movie-Catalog-Image
Then CodeBuild pushes the newly built docker images to AWS ECR
Code Deploy deploys the latest docker images to AWS ECS by updating the existing task definition to latest version
Then the existing service is stopped and a new task is started with the latest task definition
Now the newly running service task has the latest code change reflected

Create a AWS CodePipeline With the following stages

1. Source (repo from github)

2. Build using AWS CodeBuild with the following environment variables specified

    1. CONTAINER_NAME - ratings-data-image
    2. AWS_DEFAULT_REGION - ap-southeast-1
    3. REPOSITORY_URI - ECR Repository URI
    
3. Deploy to AWS ECS with the following properties specified in appspec.yml file

    1. TaskDefinition: Task Definition ARN from AWS Fargate
    2. Container Name
    3. Container Port
    
Select elevated privileges for AWS CodeBuild project to push docker images.
Provide AWS CodeBuild Role with access to ECR so that CodeBuild builds the docker images and pushes it to AWS ECR

pom.xml file uses Maven Spotify Docker plugin. This integrates Docker build with maven build
The docker terminal must be running for this

This enables maven to build the Spring boot jar file and then build the Docker images from the jar file and the 
java image with the maven command 1. clean package
