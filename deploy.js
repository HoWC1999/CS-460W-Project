/*
 * deploy.js
 * Deployment script for Tennis Club Management System.
 * This Node.js script builds the backend and frontend, merges assets, and runs the application.
 * Prerequisites: Java (with Maven) and Node.js installed.
 */
const { spawn } = require('child_process');
const fs = require('fs');
const path = require('path');

// Utility to run a command with args in given cwd
function runCommand(command, args, options = {}) {
    const proc = spawn(command, args, { stdio: 'inherit', shell: true, ...options });
    return new Promise((resolve, reject) => {
        proc.on('close', code => {
            if (code !== 0) {
                reject(new Error(`${command} ${args.join(' ')} failed with exit code ${code}`));
            } else {
                resolve();
            }
        });
    });
}

(async () => {
    try {
        const rootDir = __dirname;
        const backendDir = path.join(rootDir, 'backend');
        const frontendDir = path.join(rootDir, 'frontend');

        // 1. Build backend using pom.xml in backend folder
        console.log('📦 Building backend...');
        // Detect wrapper: mvnw (Unix) or mvnw.cmd (Windows)
        let mvnwWrapper = null;
        const mvnwPathUnix = path.join(backendDir, 'mvnw');
        const mvnwPathWin = path.join(backendDir, 'mvnw.cmd');
        if (fs.existsSync(mvnwPathWin)) {
            mvnwWrapper = mvnwPathWin;
        } else if (fs.existsSync(mvnwPathUnix)) {
            mvnwWrapper = mvnwPathUnix;
        }
        const mvnCmd = mvnwWrapper || 'mvn';
        await runCommand(mvnCmd, ['clean', 'package', '-DskipTests'], { cwd: backendDir });

        // 2. Build frontend if exists
        if (fs.existsSync(frontendDir)) {
            console.log('📦 Installing and building frontend...');
            await runCommand('npm', ['install'], { cwd: frontendDir });
            await runCommand('npm', ['run', 'build'], { cwd: frontendDir });

            // 3. Copy frontend build to backend resources
            const targetStatic = path.join(backendDir, 'src', 'main', 'resources', 'static');
            console.log('🗂️ Copying frontend build to backend static resources...');
            fs.rmSync(targetStatic, { recursive: true, force: true });
            fs.mkdirSync(targetStatic, { recursive: true });
            const buildDir = path.join(frontendDir, 'build');
            fs.cpSync(buildDir, targetStatic, { recursive: true });
        } else {
            console.log('ℹ️ No frontend directory found, skipping frontend steps.');
        }

        // 4. Discover JAR file and run application
        console.log('🚀 Starting application...');
        const targetDir = path.join(backendDir, 'target');
        const jars = fs.readdirSync(targetDir).filter(f => f.toLowerCase().endsWith('.jar'));
        if (jars.length === 0) throw new Error('No JAR file found in ' + targetDir);
        const jarPath = path.join(targetDir, jars[0]);
        await runCommand('java', ['-jar', jarPath]);

    } catch (err) {
        console.error('❌ Deployment failed:', err.message);
        process.exit(1);
    }
})();
