FROM jenkins/slave:4.3-4

USER root

RUN wget https://releases.hashicorp.com/terraform/0.12.26/terraform_0.12.26_linux_amd64.zip; \
  unzip terraform_0.12.26_linux_amd64.zip; \
  rm -f terraform_0.12.26_linux_amd64.zip; \
  mv terraform /usr/local/bin/

USER jenkins