FROM eclipse-temurin:25.0.3_9-jdk

COPY kafdrop.sh /
COPY maven/ /kafdrop/

RUN chmod +x /kafdrop.sh

EXPOSE 9000

ENTRYPOINT ["/kafdrop.sh"]
