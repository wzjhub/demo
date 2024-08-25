package com.huawei.it.execise;

import java.io.File;

public class BatchRenameFiles {
    public static void main(String[] args) {
        // 指定文件夹路径
        String folderPath = "D:\\github\\book\\book";

        // 获取文件夹对象
        File folder = new File(folderPath);

        // 检查文件夹是否存在
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("文件夹不存在或不是一个目录");
            return;
        }

        // 获取文件夹中的所有文件
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                // 检查是否为文件（排除子文件夹）
                if (file.isFile()) {
                    // 构建新文件名
                    String newFileName = file.getName().replace(" (Z-Library)","");
                    File newFile = new File(folderPath + File.separator + newFileName);

                    // 重命名文件
                    if (file.renameTo(newFile)) {
                        System.out.println("重命名成功: " + file.getName() + " -> " + newFileName);
                    } else {
                        System.out.println("重命名失败: " + file.getName());
                    }
                }
            }
        } else {
            System.out.println("文件夹中没有文件");
        }

        System.out.println("重命名完成");
    }
}
