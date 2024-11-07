module bank.rest {
  requires bank.core;
  requires bank.persistence;
  requires com.fasterxml.jackson.databind;
  requires spring.web;
  requires spring.beans;
  requires spring.boot;
  requires spring.context;
  requires spring.boot.autoconfigure;
  requires spring.webmvc;
  requires spring.core;

  opens bank.rest to spring.beans, spring.context, spring.web, spring.core;

}
