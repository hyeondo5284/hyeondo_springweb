package org.hyeondo.letter;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hyeondo.book.chap11.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class LetterController {
	
	@Autowired
	LetterDao letterDao;

	Logger logger = LogManager.getLogger();
	
	/**
	 * 받은 편지 목록
	 */
	@GetMapping("/letter/receiveList")
	public void letterReceiveList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			Model model, @SessionAttribute("MEMBER") Member member) {

		// 페이지당 행의 수와 페이지의 시작점
		final int COUNT = 10;
		int offset = (page - 1) * COUNT;

		List<Letter> receiveList = letterDao.listReceiveLetters(member.getMemberId(), offset, COUNT);
		int totalCount = letterDao.getReceiveLettersCount(member.getMemberId());
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("receiveList", receiveList);
	}
	
	/**
	 * 보낸 편지 목록
	 */
	@GetMapping("/letter/sendList")
	public void letterSendList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			Model model, @SessionAttribute("MEMBER") Member member) {

		// 페이지당 행의 수와 페이지의 시작점
		final int COUNT = 10;
		int offset = (page - 1) * COUNT;

		List<Letter> sendList = letterDao.listSendLetters(member.getMemberId(), offset, COUNT);
		int totalCount = letterDao.getSendLettersCount(member.getMemberId());
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("sendList", sendList);
	}
	
	/**
	 * 편지 보기
	 */
	@GetMapping("/letter/view")
	public void letterView(@RequestParam("letterId") String letterId,
			Model model, @SessionAttribute("MEMBER") Member member) {
		Letter letter= letterDao.getLetter(letterId, member.getMemberId());
		model.addAttribute("letter", letter);
	}
	
	/**
	 * 편지 작성 화면
	 */
	@GetMapping("/letter/sendForm")
	public void letterSendForm(@RequestParam("receiverId") String receiverId, @RequestParam("receiverName") String receiverName,
			Model model, Letter letter) {
		letter.setReceiverId(receiverId);
		letter.setReceiverName(receiverName);
		model.addAttribute("letter", letter);
	}
	
	/**
	 * 편지 작성
	 */
	@PostMapping("/letter/send")
	public String letterSend(@RequestParam("receiverId") String receiverId, @RequestParam("receiverName") String receiverName,
			Letter letter, @SessionAttribute("MEMBER") Member member) {
		letter.setSenderId(member.getMemberId());
		letter.setSenderName(member.getName());
		letterDao.sendLetter(letter);
		return "redirect:/app/letter/sendList";
	}
	
	/**
	 * 편지 삭제
	 */
	@GetMapping("/letter/delete")
	public String delete(@RequestParam("letterId") String letterId,
			@SessionAttribute("MEMBER") Member member) {
		int updatedRows = letterDao.deleteLetter(letterId,
				member.getMemberId());

		// 권한 체크 : 글이 삭제되었는지 확인
		if (updatedRows == 0)
			// 글이 삭제되지 않음. 자신이 쓴 글이 아님
			throw new RuntimeException("No Authority!");

		return "redirect:/app/letter/receiveList";
	}
}
