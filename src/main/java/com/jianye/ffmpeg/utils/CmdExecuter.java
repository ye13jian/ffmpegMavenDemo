package com.jianye.ffmpeg.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * CMD执行器
 * @author <a href="mailto:963552657@qq.com">jianye</a>
 *
 */
public class CmdExecuter {
	
	private CmdExecuter() {}
	
	/**
	 * 执行命令
	 * @param cmd 命令
	 * @param getter 结果集处理工具类
	 */
	public static void exec (List<String> cmd, IStringGetter getter) {
		Process process = null;
		BufferedReader stdout= null;
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(cmd);
			// 处理进程调用ffmepg阻塞问题
			builder.redirectErrorStream(true);
			// 开始执行命令
			process = builder.start();
			stdout = new BufferedReader(
					new InputStreamReader(process.getInputStream()));
			String line = null;
			while ((line = stdout.readLine()) != null) {
				// TODO 判断有待优化
				if (null != getter) {
					getter.dealString(line);
				}
			}
//			process.waitFor();
//			stdout.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != process) {
					process.waitFor();
				}
				if (null != stdout) {
					stdout.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 执行命令
	 * @param cmd 命令行
	 */
	public static String exec (List<String> cmd) {
		String convert_time = null;
		Process proc = null;
		BufferedReader stdout= null;
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(cmd);
			// 处理进程调用ffmpeg程序阻塞问题
			builder.redirectErrorStream(true);
			// 执行命令
			proc = builder.start();
			stdout = new BufferedReader(
					new InputStreamReader(proc.getInputStream()));
			String line;
			int lineNumber = 1;
			List<String> returnStringList = new LinkedList<String>();
			while ((line = stdout.readLine()) != null) {
				System.out.println("第" + lineNumber + "行：" + line);
				lineNumber += 1;
				returnStringList.add(line);
			}
			String info = "";
			for (int i = returnStringList.size() - 1; i >= 0; i--) {
				if (null != returnStringList.get(i) && returnStringList.get(i).startsWith("frame=")) {
					info = returnStringList.get(i);
					break;
				}
			}
			if (null != info) {
				convert_time = info.split("time=")[1].split("bitrate=")[0].trim();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != proc) {
					proc.waitFor();
				}
				if (null != stdout) {
					stdout.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return convert_time;
	}
}
