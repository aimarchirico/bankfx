module bank.persistence {
  requires transitive bank.core;
  requires transitive com.fasterxml.jackson.databind;

  exports bank.persistence;
}
