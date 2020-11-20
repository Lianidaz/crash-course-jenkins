FROM jenkins/slave:4.3-4

USER root

RUN apt-get update; \
  apt-get install -y apt-transport-https ca-certificates gnupg-agent software-properties-common; \
  curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add -; \
  add-apt-repository \
  "deb [arch=amd64] https://download.docker.com/linux/debian \
  $(lsb_release -cs) \
  stable"; \
  curl -sL https://deb.nodesource.com/setup_10.x | bash -; \
  apt-get update; \
  apt-get install -y nodejs build-essential docker-ce-cli; \
  wget https://releases.hashicorp.com/terraform/0.12.26/terraform_0.12.26_linux_amd64.zip; \
  unzip terraform_0.12.26_linux_amd64.zip; \
  rm -f terraform_0.12.26_linux_amd64.zip; \
  mv terraform /usr/local/bin/

USER jenkins