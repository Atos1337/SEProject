FROM gradle
WORKDIR /home/gradle/OneMorePet
RUN git clone https://github.com/Atos1337/SEProject.git .
RUN gradle dependencies --no-daemon
ENTRYPOINT gradle bootRun --no-daemon