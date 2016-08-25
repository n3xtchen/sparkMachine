/*
 * Ngram.scala
 * Copyright (C) 2016 n3xtchen <echenwen@gmail.com>
 *
 * Distributed under terms of the GPL-2.0 license.
 */

package org.next.ngram

import scala.io.Source

object Ngram {

	private var gramTrie = PrefixMap.empty[Int];

  private def split(article: String) = ???

  private def ngram(n: Int)(words: List[Char]) = {
    /* ngram 算法 */
    val len = words.length
    // assert(n <= len, "长度不得短语句子总长度")
    if(n <= len) {
      for (i <-  0 to (words.length-n))
        yield {
          for (j <- 0 to n-1) yield words(i+j)
        }.mkString
    } else Nil
  }

	def main(args: Array[String]) {
    val filePath = args(0)

    val lines = Source.fromFile(filePath).getLines().toList.mkString

    val sentences = lines.split("[^\u4e00-\u9fa5]").toList

    val stopWords = List("和", "与", "你", "我", "两", "这", "把", "自", "已", "那", "个", "他", "您",
      "它", "们", "是", "的", "一", "了", "在", "没", "有", "么", "到", "以", "可", "来", "去", "最",
      "出", "发", "不", "要", "因", "为", "如", "觉", "分", "进", "入", "很", "然", "知", "目标", "孩子",
      "空中", "时间", "同时", "开始", "看", "应", "该", "目光", "仁", "选择")

    for (sentence <- sentences; n <- 2 to 4) {
      val words = sentence.toCharArray.toList
      val nGrams = ngram(n)(words)
      for (w <- nGrams if ! stopWords.exists(x => w.contains(x))) {
        if (gramTrie.withPrefix(w) != PrefixMap.empty[Int]) {
          gramTrie += ((w, gramTrie.withPrefix(w).value.getOrElse(0)+1))
        } else {
          gramTrie += ((w, 1))
        }
		  }
    }

    val sorted_gram = gramTrie.toSeq.filter(_._2 > -1).sortWith(_._2 > _._2)
    sorted_gram.take(100).foreach(println)
    println(sorted_gram.length)
	}

}

