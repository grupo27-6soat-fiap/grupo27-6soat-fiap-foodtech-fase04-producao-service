# temp container to build using gradle
FROM gradle:8.4.0-jdk17 AS TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY build.gradle settings.gradle $APP_HOME

COPY gradle $APP_HOME/gradle
COPY --chown=gradle:gradle . /home/gradle/src
USER root
RUN chown -R gradle /home/gradle/src


# Ensure Docker CLI tools are installed
#USER root
# Ensure Docker CLI tools and socat are installed
# Ensure Docker CLI tools and socat are installed
# Ensure Docker CLI tools and socat are installed
#RUN apt-get update && \
#    apt-get install -y curl socat && \
#    curl -L https://download.docker.com/linux/static/stable/x86_64/docker-20.10.7.tgz | tar -xz -C /usr/local/bin --strip-components=1 docker/docker



# Set environment variables for Docker inside the container
#ENV DOCKER_HOST=host.docker.internal
#ENV TESTCONTAINERS_DOCKER_SOCKET_OVERRIDE=host.docker.internal

 #Run socat to forward the Docker socket
#RUN nohup socat UNIX-LISTEN:/var/run/docker.sock,fork TCP:host.docker.internal:2375 &


#RUN gradle build
COPY . .


RUN gradle clean build

#RUN gradle clean build --no-daemon



FROM amazoncorretto:17-alpine3.18-jdk

ENV ARTIFACT_NAME=producao-service-fase-4-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app/

# Set the working directory in the container
WORKDIR $APP_HOME

# Copy the Gradle build files
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .

EXPOSE 8080

ENTRYPOINT exec java -jar ${ARTIFACT_NAME}