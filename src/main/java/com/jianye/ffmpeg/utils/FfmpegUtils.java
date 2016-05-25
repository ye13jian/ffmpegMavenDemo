package com.jianye.ffmpeg.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * ffmpeg工具类
 * <dl>
 * 	<dd>ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等</dd>
 * 	<dd>ffmpeg无法解析的文件格式(wmv9，rm，rmvb等),</dd>
 * </dl>
 * @author <a href="mailto:963552657@qq.com">jianye</a>
 *
 */
public class FfmpegUtils implements IStringGetter {

	public static void main(String[] args) {
		String infile = "";
		String outfile = "";
		String avitoflv = "ffmpeg -i " + infile + " -ar 22050 -ab 56 -f flv -y -s 320x240 " + outfile;
		String flvto3gp = "ffmpeg -i " + infile
				+ " -ar 8000 -ac 1 -acodec amr_nb -vcodec h263 -s 176x144 -r 12 -b 30 -ab 12 " + outfile;
		String avito3gp = "ffmpeg -i " + infile
				+ " -ar 8000 -ac 1 -acodec amr_nb -vcodec h263 -s 176x144 -r 12 -b 30 -ab 12 " + outfile;
		String avitojpg = "ffmpeg -i " + infile + " -y -f image2 -ss 00:00:10 -t 00:00:01 -s 350x240 " + outfile;
		System.out.println("执行：" + avitoflv);
		System.out.println("执行：" + flvto3gp);
		System.out.println("执行：" + avito3gp);
		System.out.println("执行：" + avitojpg);
	}

	public FfmpegUtils() {
	}

	public FfmpegUtils(String ffmpegPath) {
		this.ffmpegPath = ffmpegPath;
	}

	/**
	 * ffmpeg URL
	 */
	private String ffmpegPath = "F:/project/hf-jwysp/tool/bin/ffmpeg.exe";
	
	/**
	 * mencoder.exe路径 
	 * 转码速度很快
	 */
	private String mencoderPath = "F:/project/hf-jwysp/tool/bin/MPlayer/mencoder.exe";

	public void dealString(String str) {
		System.out.println("dealString：" + str);
	}

	public String getFfmpegPath() {
		return ffmpegPath;
	}

	public void setFfmpegPath(String ffmpegPath) {
		this.ffmpegPath = ffmpegPath;
	}

	/**
	 * @return the mencoderPath
	 */
	public String getMencoderPath() {
		return mencoderPath;
	}

	/**
	 * @param mencoderPath the mencoderPath to set
	 */
	public void setMencoderPath(String mencoderPath) {
		this.mencoderPath = mencoderPath;
	}

	/**
	 * 视频格式转换(flv)
	 */
	public void videoTransfer(String inputfile, String outputfile) {
		List<String> cmd = new LinkedList<String>();
		cmd.add(ffmpegPath);
		cmd.add("-y"); // 覆盖以前生成的文件
		cmd.add("-i");  
        cmd.add(inputfile);
        // cmd.add("-b");
        // cmd.add("600"); // 设置比特率
        cmd.add("-ab");  
        cmd.add("56");  
        cmd.add("-ar");  
        cmd.add("22050");
        cmd.add("-f");
        cmd.add("flv"); // 转换为flv格式
        cmd.add("-qscale"); // 清晰度 -qscale 4 为最好但文件大, -qscale 6就可以了
        cmd.add("8");  
        cmd.add("-r");  
        cmd.add("15");  
        // cmd.add("-s");  
        // cmd.add("600x500"); // 视频解析度(分辨率,很好cpu)
        cmd.add(outputfile);
		CmdExecuter.exec(cmd, this);
		// 清空命令行
		cmd.clear();
	}
	
	/**
	 * 使用mencoder进行视频转码
	 * @param inputfile
	 * @param outputfile
	 */
	public void videoTransferByMencoder(String inputfile, String outputfile) {
		// 无法做到更新文件
		File outputFile = new File(outputfile);
		if (outputFile.exists()) {
			System.out.println("outputfile exist!");
			return;
		}
		List<String> cmd = new ArrayList<String>();
		cmd.add(mencoderPath);
        cmd.add(inputfile);    
        cmd.add("-oac");    
        cmd.add("mp3lame");    
        cmd.add("-lameopts");    
        cmd.add("preset=64");    
        cmd.add("-ovc");    
        cmd.add("xvid");    
        cmd.add("-xvidencopts");    
        cmd.add("bitrate=600");    
        cmd.add("-of");
        // 转换为avi
        cmd.add("avi");    
        cmd.add("-o");    
        cmd.add(outputfile);
        CmdExecuter.exec(cmd, this);
        cmd.clear();
	}

	/**
	 * 截取第一帧图片
	 */
	public void makeScreenCut(String ordinaryPath, String imageSavePath) {
		makeScreenCut(ordinaryPath, imageSavePath, "350*240");
	}

	/**
	 * 截取第一帧图片
	 */
	public void makeScreenCut(String ordinaryPath, String imageSavePath, String screenSize) {
		List<String> cmd = new ArrayList<String>();
		cmd.add(ffmpegPath);
		cmd.add("-i");
		cmd.add(ordinaryPath); // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）
		cmd.add("-y"); // 覆盖输出的文件
		cmd.add("-f");
		cmd.add("image2");
		cmd.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间
		// cmd.add("17"); // 添加起始时间为第17秒
		cmd.add("1.000"); // 添加开始时间第1秒
		cmd.add("-t"); // 添加参数＂-t＂，该参数指定持续时间
		cmd.add("0.001"); // 添加持续时间为1毫秒
		cmd.add("-s"); // 添加参数＂-s＂，该参数指定截取的图片大小
		cmd.add(screenSize); // 添加截取的图片大小为350*240
		cmd.add(imageSavePath); // 添加截取的图片的保存路径
		CmdExecuter.exec(cmd, this);
		// 清空命令
		cmd.clear();
	}

	/**
	 * 视频转音频(8k16bit)
	 */
	public void videoTransferAudio(String inputfile, String outputfile) {
		List<String> cmd = new ArrayList<String>();
		cmd.add(ffmpegPath);
		cmd.add("-loglevel");
		cmd.add("quiet");
		cmd.add("-i");
		cmd.add(inputfile);
		cmd.add("-vn");
		cmd.add("-acodec");
		cmd.add("pcm_s16le");
		cmd.add("-ac");
		cmd.add("1");
		cmd.add("-ar");
		cmd.add("8000");
		cmd.add("-y");
		cmd.add(outputfile);
		CmdExecuter.exec(cmd, this);
		cmd.clear();
	}
}
