# Define the environment
FROM openjdk:8

ENV SCALA_VERSION=2.11
ENV SCALA_VERSION_MINOR=2.11.8

ENV WORK_DIR="/app"

ENV JAR_NAME="alert-assembly-0.2.jar"
ENV JAR_PATH="target/scala-${SCALA_VERSION}/${JAR_NAME}"

ENV WEBDRIVER_NAME="chromedriver"
ENV WEBDRIVER_PATH="${WORK_DIR}/${WEBDRIVER_NAME}"

RUN wget -O- "http://downloads.lightbend.com/scala/${SCALA_VERSION_MINOR}/scala-${SCALA_VERSION_MINOR}.tgz" \
    | tar xzf - -C /usr/local --strip-components=1

RUN mkdir ${WORK_DIR}

ADD ./target/${WEBDRIVER_NAME} ${WORK_DIR}/
ADD ${JAR_PATH} ${WORK_DIR}/
RUN ls -la ${WORK_DIR}


WORKDIR ${WORK_DIR}

RUN apt -yqq update \
    && apt install -y libglib2.0-0 libnss3 libx11-6

CMD scala /${WORK_DIR}/${JAR_NAME}
#CMD /bin/sh
