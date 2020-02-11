# Define the environment
FROM openjdk:8

ENV DEBIAN_FRONTEND noninteractive
ENV DEBCONF_NONINTERACTIVE_SEEN true

# Set timezone
#RUN echo "US/Eastern" > /etc/timezone && \
#    dpkg-reconfigure --frontend noninteractive tzdata
#
## Create a default user
#RUN useradd automation --shell /bin/bash --create-home
#
#RUN apt-get -yqq update && \
#    apt-get -yqq install curl unzip && \
#    apt-get -yqq install xvfb tinywm && \
#    apt-get -yqq install fonts-ipafont-gothic xfonts-100dpi xfonts-75dpi xfonts-scalable xfonts-cyrillic && \
#    apt-get -yqq install python && \
#    rm -rf /var/lib/apt/lists/*
#
## Install Supervisor
#RUN curl -sS -o - https://bootstrap.pypa.io/ez_setup.py | python && \
#    easy_install -q supervisor
#
## Install Chrome WebDriver
#RUN CHROMEDRIVER_VERSION=`curl -sS chromedriver.storage.googleapis.com/LATEST_RELEASE` && \
#    mkdir -p /opt/chromedriver-$CHROMEDRIVER_VERSION && \
#    curl -sS -o /tmp/chromedriver_linux64.zip http://chromedriver.storage.googleapis.com/$CHROMEDRIVER_VERSION/chromedriver_linux64.zip && \
#    unzip -qq /tmp/chromedriver_linux64.zip -d /opt/chromedriver-$CHROMEDRIVER_VERSION && \
#    rm /tmp/chromedriver_linux64.zip && \
#    chmod +x /opt/chromedriver-$CHROMEDRIVER_VERSION/chromedriver && \
#    ln -fs /opt/chromedriver-$CHROMEDRIVER_VERSION/chromedriver /usr/local/bin/chromedriver
#
## Install Google Chrome
#RUN curl -sS -o - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - && \
#    echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list && \
#    apt-get -yqq update && \
#    apt-get -yqq install google-chrome-stable && \
#    rm -rf /var/lib/apt/lists/*

## Configure Supervisor
#ADD ./etc/supervisord.conf /etc/
#ADD ./etc/supervisor /etc/supervisor
#
## Default configuration
#ENV DISPLAY :20.0
#ENV SCREEN_GEOMETRY "1440x900x24"
#ENV CHROMEDRIVER_PORT 4444
#ENV CHROMEDRIVER_WHITELISTED_IPS "127.0.0.1"
#ENV CHROMEDRIVER_URL_BASE ''
#ENV CHROMEDRIVER_EXTRA_ARGS ''
#
#EXPOSE 4444
#
#VOLUME [ "/var/log/supervisor" ]
#
#CMD ["/usr/local/bin/supervisord", "-c", "/etc/supervisord.conf"]

## Default configuration
#ENV DISPLAY :20.0
#ENV SCREEN_GEOMETRY "1440x900x24"
#ENV CHROMEDRIVER_PORT 4444
#ENV CHROMEDRIVER_WHITELISTED_IPS "127.0.0.1"
#ENV CHROMEDRIVER_URL_BASE ''
#ENV CHROMEDRIVER_EXTRA_ARGS ''
RUN apt-get -yqq update
RUN apt install -y libglib2.0-0 libnss3 libx11-6

ENV JAR_NAME="alert-1.0-SNAPSHOT-jar-with-dependencies.jar"
ENV WORK_DIR="app"
ENV WEBDRIVER_PATH="/${WORK_DIR}/chromedriver"

#RUN wget -O- "http://downloads.lightbend.com/scala/2.11.8/scala-2.11.8.tgz" \
#    | tar xzf - -C /usr/local --strip-components=1

#VOLUME /${WORK_DIR}?
RUN mkdir /${WORK_DIR}
WORKDIR /${WORK_DIR}

ADD ./target/${JAR_NAME} /${WORK_DIR}/
ADD ./chromedriver_linux64/chromedriver /${WORK_DIR}/

RUN ls -la  /${WORK_DIR}
CMD java -jar ${JAR_NAME}