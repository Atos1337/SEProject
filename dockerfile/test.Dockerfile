FROM gradle
WORKDIR /home/gradle/OneMorePet
RUN git clone https://github.com/Atos1337/SEProject.git .
RUN gradle dependencies --no-daemon
RUN gradle build --no-daemon
RUN gradle test --no-daemon