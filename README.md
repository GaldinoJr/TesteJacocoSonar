


# Configurando o Jacoco
Primeiro, adicione ao build.gradle de nível de projeto:
```groovy
buildscript {
 dependencies {
	 ...
	 classpath 'com.dicedmelon.gradle:jacoco-android:0.1.4'
	 }
 }
 ```

Em seguida crie um arquivo chamado `./jacoco.gradle` e adicione essas linhas a ela:
```groovy
jacocoAndroidUnitTestReport {
 excludes +=
 [
	 '**/AutoValue_*.*',
	 '**/*JavascriptBridge.class',
	 '**/MainPresenter2**',
	 '**/*Activity*'
 ]
 csv.enabled false
 html.enabled true
 xml.enabled true
```

Depois adicione no build.gradle do seu módulo principal/base (normalmente chamado de :app), as seguintes linhas abaixo do apply dos outros plugins:

```groovy
apply plugin: 'jacoco-android'
apply from: './jacoco.gradle'

jacoco {
toolVersion = "0.8.4" }
```
Com essa configuração você pode rodar o cover de teste unitário e visualizar no arquivo index.html na pasta app/build/reports/jacoco/jacocoTest{nome da sua flavor}UnitTestReport/html/index.html
```bash
./gradlew jacocoTest{nome da sua flavor}gUnitTestReport
 ```

![enter image description here](https://raw.githubusercontent.com/GaldinoJr/TesteJacocoSonar/master/app/src/main/res/drawable-v24/Captura%20de%20Tela%202020-04-01%20%C3%A0s%2020.14.58.png)

# Adicione o plugin do SonarQube
  Primeiro, adicione ao build.gradle de nível de projeto:

 ```groovy
 repositories {
 ..
 maven { url "https://plugins.gradle.org/m2/" } }
 ```

```groovy
buildscript {
 dependencies {
	 ..
	 classpath("org.springframework.boot:spring-boot-gradle-plugin:1.5.4.RELEASE")
	 classpath "org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:2.8"
	 }
 }
```

```groovy
allprojects {
	 maven { url "https://plugins.gradle.org/m2/" }
}
```

Em seguida crie um arquivo chamado `./sonarqube.gradle` e adicione essas linhas a ela:
obs.: ver o caminho do .xml gerado pelo jacoco (muda conforme a flavor), e configure corretamente os itens: sonar.coverage.jacoco.xmlReportPaths e sonar.junit.reportsPath
```groovy
sonarqube {
	properties {
		 sonarqube {
  properties {
  property "sonar.projectName", "TestJacocoSonar2"
  property "sonar.projectKey", "TestJacocoSonar2"
  property "sonar.host.url", "http://localhost:9000"
  // colocar para rodar no server
  // property "sonar.host.url", "url do host"
  // property "sonar.login", "key"
  // property "sonar.password", ""
  property "sonar.exclusions",
                    '**/MainPresenter2**,' +
                    '**/*Activity*'

  property "sonar.sources", "src/main"
  property "sonar.java.binaries", "build/tmp/kotlin-classes"
  property "sonar.tests", "src/test"
  property "sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/jacocoTestDebugUnitTestReport/jacocoTestDebugUnitTestReport.xml"
  property "sonar.junit.reportsPath", "build/reports/jacoco/jacocoTestDebugUnitTestReport/"
  property "sonar.java.coveragePlugin", "jacoco"
  property "sonar.android.lint.report", "build/reports/lint-results.xml"
  }
}
	}
}
```

Depois adicione no build.gradle do seu módulo principal/base (normalmente chamado de :app), as seguintes linhas abaixo do apply dos outros plugins:

```groovy
apply plugin: "org.sonarqube"
apply from: './sonarqube.gradle'
```
Com essa configuração você pode rodar os cover de teste unitário e subir o projeto no SonarQube rodando:

```bash
./gradlew jacocoTestProdDebugUnitTestReport sonarqube
```

![enter image description here](https://raw.githubusercontent.com/GaldinoJr/TesteJacocoSonar/master/app/src/main/res/drawable-v24/Captura%20de%20Tela%202020-04-01%20%C3%A0s%2020.13.28.png)

Obs.: O comando muda conforme a flavor, rode o comando:

```bash
./gradlew tasks
```
Para obter o nome da task corretamente


Com essa configuração você pode subir o projeto no SonarQube rodando:
```bash
./gradlew sonarqube
```

É possível reconfigurar o arquivo sonar, pra contar cover de teste de UI, após fazer as devidas configurações,você pode subir o projeto no SonarQube e com os testes de cobertura Unitário + Interface rodando:
```bash
./gradlew clean connectedAndroidTest test createDebugCoverageReport combinedTestReportDebug sonarqube
```

Dependendo dos productFlavors que vc tiver, esses comandos tem que ser alterados.

# Importante
Para rodar localmente, igual está configurado, é preciso que tenha um server local rodando, se você quiser fazer isso, e estiver usando mac, pode utilizar o seguinte link:
https://mobiosolutions.com/install-sonarqube-installation-guide-mac-os/


# Conclusão
O cover fica um pouco diferente do jacoco e do sonar, acredito, que o sonar pega as duas colunas do jacoco missed instructions e missed branches e faz uma médias (missed instructions + missed branches) / 2.
Desculpem, foi o mais perto que consegui chegar de fazer um bom trabalho.