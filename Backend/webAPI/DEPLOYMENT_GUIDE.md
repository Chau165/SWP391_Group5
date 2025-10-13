# Tomcat Deployment & Debug Guide

This guide shows how to build, deploy, and debug your `webAPI1` servlet project using Apache Tomcat.

## Prerequisites

- Apache Tomcat 9+ installed
- Java 8+ installed
- Apache Ant installed (for building)

## Building the WAR file

1. **Build with Ant:**

   ```bash
   ant clean dist
   ```

   This creates `dist/webAPI1.war`

2. **Alternative - Build with NetBeans:**
   - Open project in NetBeans
   - Right-click project → Clean and Build
   - WAR file will be in `dist/webAPI1.war`

## Deploying to Tomcat

### Method 1: Copy WAR file

```bash
# Copy WAR to Tomcat webapps directory
copy "dist\webAPI1.war" "%CATALINA_HOME%\webapps\"
```

### Method 2: Tomcat Manager (recommended)

1. Start Tomcat: `%CATALINA_HOME%\bin\startup.bat`
2. Open http://localhost:8080/manager/html
3. Upload `dist/webAPI1.war` via the web interface

## Setting up Remote Debugging

### Option 1: Environment Variable (Windows)

```batch
set CATALINA_OPTS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000
%CATALINA_HOME%\bin\startup.bat
```

### Option 2: Modify startup script

Edit `%CATALINA_HOME%\bin\setenv.bat` (create if it doesn't exist):

```batch
set CATALINA_OPTS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000
```

### Option 3: Direct command

```batch
%CATALINA_HOME%\bin\catalina.bat jpda start
```

## Debug Workflow

1. **Build and deploy:**

   ```bash
   ant clean dist
   copy "dist\webAPI1.war" "%CATALINA_HOME%\webapps\"
   ```

2. **Start Tomcat with debugging:**

   ```batch
   set CATALINA_OPTS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000
   %CATALINA_HOME%\bin\startup.bat
   ```

3. **Attach VS Code debugger:**
   - Press `F5` or go to Run → Start Debugging
   - Select "Attach to Tomcat" from the dropdown
   - Set breakpoints in your Java code
   - Access your API endpoints to trigger breakpoints

## API Endpoints

After deployment, your APIs will be available at:

- Swagger UI: http://localhost:8080/webAPI1/swagger-ui/
- Login: http://localhost:8080/webAPI1/api/login
- Register: http://localhost:8080/webAPI1/api/register
- Get Packages: http://localhost:8080/webAPI1/api/getpackages
- Get Stations: http://localhost:8080/webAPI1/api/getstations

## Troubleshooting

### Port 8000 already in use

Change the debug port in both:

- Tomcat startup: `address=*:8001`
- VS Code launch.json: `"port": "8001"`

### Tomcat not starting

Check logs in `%CATALINA_HOME%\logs\catalina.out`

### Debugger not connecting

1. Verify Tomcat started with debug output: "Listening for transport dt_socket at address: 8000"
2. Check Windows Firewall isn't blocking port 8000
3. Ensure VS Code Java Extension Pack is installed

### Hot reload changes

For faster development:

1. Use NetBeans with Tomcat integration for automatic deployment
2. Or use `ant compile` + copy classes to `%CATALINA_HOME%\webapps\webAPI1\WEB-INF\classes\`

## VS Code Debug Configurations

Your `.vscode/launch.json` now includes:

- **Launch Java App**: Runs SwaggerConfigServlet directly (limited functionality)
- **Attach to Tomcat**: Attaches to running Tomcat instance (recommended for full servlet debugging)
