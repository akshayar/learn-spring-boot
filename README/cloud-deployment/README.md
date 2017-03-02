# Cloud Deployment
[Source Code](../../learn-spring-boot)

[Project Home Page](https://github.com/akshayar/learn-spring-boot)

## The Example

1. Deploy on Heroku and CloudFoundry

## Heroku

1. Refer an excellent step by step guide for deploying Java application at [Heroku-Getting Started with Java ](https://devcenter.heroku.com/articles/getting-started-with-java#introduction)
2. There a few modifications. 
  1. Checkout this source code. 
  2. From the root directory instead of deployment using `git push heroku master` use `git subtree push --prefix learn-spring-boot heroku master` . This had to be done since the pom.xml is not in the root directory but in a sub-direcotry by name learn-spring-boot. 
3. Here are the sequence of commands to run -
```
heroku create
git subtree push --prefix learn-spring-boot heroku master
heroku ps:scale web=1
heroku logs 
heroku open
## In the URL append security/ping
heroku ps:scale web=0
```
