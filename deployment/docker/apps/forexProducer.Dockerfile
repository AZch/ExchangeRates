FROM openjdk:11-slim-buster as prepare

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
COPY ./producers/parsers/ForexParser/target/ForexParser-0.0.1-SNAPSHOT.jar ./app.jar

COPY ./common/SeleniumEngineStarter/src/main/resources/drivers/chromedriver /Users/antonzhuikov/IdeaProjects/ExchangeRatesJava/common/SeleniumEngineStarter/src/main/resources/drivers/chromedriver_mac

CMD ["java", "-jar", "app.jar"]
