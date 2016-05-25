package com.jianye.ffmpeg;

/**
 * 执行EXE测试
 * @author <a href="mailto:963552657@qq.com">jianye</a>
 *
 */
public class ExecuteTest {

	public static void main(String[] args) {
		openWinExe();
	}

	public static void openWinExe() {
		Runtime run = Runtime.getRuntime();
		Process proc = null;
		try {
//			String command = "cmd";
			String command = "notepad";
			proc = run.exec(command);
			System.out.println(proc.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
