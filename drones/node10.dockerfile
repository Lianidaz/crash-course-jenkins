FROM jenkins/slave:4.3-4

USER root

RUN curl -sL https://deb.nodesource.com/setup_10.x | bash -; \
  apt-get install -y nodejs build-essential

USER jenkins