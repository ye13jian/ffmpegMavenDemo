package com.jianye.ffmpeg;

import com.jianye.ffmpeg.utils.FfmpegUtils;

/**
 * ffmpeg工具测试
 * @author <a href="mailto:963552657@qq.com">jianye</a>
 *
 */
public class FfmpegTest {
	public static void main(String[] args) {
		String rootPath = "F:/project/hf-jwysp/tool/bin/";
		String inputfile1 = rootPath + "input/" + "[阳光电影www.ygdy8.com].神秘博士：2015圣诞特别篇.BD.720p.中英双字幕.rmvb";
		String inputfile2 = rootPath + "input/" + "[阳光电影www.ygdy8.com].头脑特工队.BD.720p.国英双语.中英双字幕.mkv";
		FfmpegUtils ffmpeg = new FfmpegUtils();
		
		// 截取第一帧图片
		String outputImg1 = rootPath + "output/1.jpg";
		ffmpeg.makeScreenCut(inputfile1, outputImg1);
		System.out.println();
		String outputImg2 = rootPath + "output/2.jpg";
		ffmpeg.makeScreenCut(inputfile2, outputImg2);
		System.out.println();
		
		// 使用mencoder转码 634MB --> 272MB,需要时间：1hour23min
		// String outputVideoAvi = rootPath + "output/1.avi";
		// ffmpeg.videoTransferByMencoder(inputfile1, outputVideoAvi);
		
		// 使用ffmpeg转码 634---> 702MB,需要时间：30min，太耗cpu性能
		// String outputVideoFlv = rootPath + "output/1.flv";
		// ffmpeg.videoTransfer(inputfile1, outputVideoFlv);
		
		// 转成音频
		String outputWav = rootPath + "output/1.wav";
		ffmpeg.videoTransferAudio(inputfile1, outputWav);
	}
}
