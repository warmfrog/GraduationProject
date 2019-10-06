# 本科毕业论文 移动端共享图书系统设计与开发

## 编译生成 pdf 论文

根据指导员分享的毕业论文 LATEX 模板，在 Windows 下下载了 TextLive（约 5 个 G），在 IDEA 下尝试一天未果后，在 VS Code下根据提示安装了插件，然后成功编译。VS Code 用的就是之前下载的 TextLive。之后在 ubuntu 下安装 TextLive-full，同样使用 VS Code，并未成功。

## 利用 [Pandoc][1] 生成 Word, Markdown 等其他版本

**转换 Latex 为 Markdown 文档**

```bash
pandoc MainBody.tex -f latex -t markdown --bibliography=ReferenceBase.bib  -s -o MainBody.md
```


[1]: https://www.pandoc.org/