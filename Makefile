.PHONY: run submit

run:
	{ \
		mvn -f account/pom.xml -Dtest=sampleTest.java test || true; \
		mvn -f client/pom.xml -Dtest=sampleTest.java test || true; \
		junit-merge -o xunitreport.xml account/target/surefire-reports/*sampleTest.xml client/target/surefire-reports/*sampleTest.xml; \
	}

submit:
	{ \
		mvn -f account/pom.xml -Dtest=mainTest.java test || true; \
		mvn -f client/pom.xml -Dtest=mainTest.java test || true; \
		junit-merge -o xunitreport.xml account/target/surefire-reports/*mainTest.xml client/target/surefire-reports/*mainTest.xml; \
	}
