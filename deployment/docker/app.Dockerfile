FROM maven:3.8.2-openjdk-11-slim as build

WORKDIR /app
COPY ./src ./src
COPY ./pom.xml ./pom.xml

RUN mvn clean install

FROM openjdk:11-slim-buster as prepare

#RUN apt-get update; apt-get clean
#
#RUN useradd apps
#RUN mkdir -p /home/apps && chown apps:apps /home/apps
#
## Install x11vnc.
#RUN apt-get install -y x11vnc
## Install xvfb.
#RUN apt-get install -y xvfb
## Install fluxbox.
#RUN apt-get install -y fluxbox
## Install wget.
#RUN apt-get install -y wget
## Install wmctrl.
#RUN apt-get install -y wmctrl
#
#RUN apt-get install -y gnupg2
#
## Set the Chrome repo.
#RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
#    && echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list
## Install Chrome.
#RUN apt-get update && apt-get -y install google-chrome-stable

# Install deps + add Chrome Stable + purge all the things
RUN apt-get update && apt-get install -y \
	apt-transport-https \
	ca-certificates \
	curl \
	gnupg \
	--no-install-recommends \
	&& curl -sSL https://dl.google.com/linux/linux_signing_key.pub | apt-key add - \
	&& echo "deb https://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list \
	&& apt-get update && apt-get install -y \
	google-chrome-stable \
	fontconfig \
	fonts-ipafont-gothic \
	fonts-wqy-zenhei \
	fonts-thai-tlwg \
	fonts-kacst \
	fonts-symbola \
	fonts-noto \
	fonts-freefont-ttf \
	--no-install-recommends \
	&& apt-get purge --auto-remove -y curl gnupg \
	&& rm -rf /var/lib/apt/lists/*

# Add Chrome as a user
RUN groupadd -r chrome && useradd -r -g chrome -G audio,video chrome \
	&& mkdir -p /home/chrome && chown -R chrome:chrome /home/chrome

# Run Chrome non-privileged
USER chrome

FROM prepare as app

WORKDIR /app
COPY --from=build /app/target/ExchangeRatesJava-0.0.1-SNAPSHOT.jar ./ExchangeRatesJava-0.0.1-SNAPSHOT.jar

COPY ./src/main/resources/selenium/drivers/chromedriver /Users/antonzhuikov/IdeaProjects/ExchangeRatesJava/src/main/resources/selenium/drivers/chromedriver

CMD ["java", "-jar", "ExchangeRatesJava-0.0.1-SNAPSHOT.jar"]
