FROM gradle:7.3.3
WORKDIR /home/gradle/OneMorePet
ADD . .
RUN gradle dependencies --no-daemon
RUN gradle build --no-daemon
