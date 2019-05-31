package org.hyeondo.letter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class LetterDao {
	
	//보낸 편지 목록
	static final String LIST_SEND_LETTERS = "select letterId, title, receiverId, receiverName, cdate from letter where senderId=?";
	
	//받은 편지 목록
	static final String LIST_RECEIVE_LETTERS = "select letterId, title, senderId, senderName, cdate from letter where receiverId=?";

	//보낸 편지 보기
	static final String GET_SEND_LETTER = "select letterId, title, content, receiverId, receiverName, cdate from letter where letterId=? and senderId=?";
	
	//받은 편지 보기
	static final String GET_RECEIVE_LETTER = "select letterId, title, content, senderId, senderName, cdate from letter where letterId=? and receiverId=?";

	//편지 보내기 sender는 hidden
	static final String ADD_LETTER = "insert letter(title, content, receiverId, receiverName, senderId, senderName) values(?,?,?,?,?,?)";

	//받은 편지 삭제
	static final String DELETE_LETTER = "delete from letter where (letterId, receiverId) = (?,?)";
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	RowMapper<Letter> letterRowMapper = new BeanPropertyRowMapper<>(Letter.class);
	
	/**
	 * 보낸 편지 목록
	 */
	public List<Letter> listSendLetters(String senderId) {
		return jdbcTemplate.query(LIST_SEND_LETTERS, letterRowMapper, senderId);
	}
	
	/**
	 * 받은 편지 목록
	 */
	public List<Letter> listReceiveLetters(String receiverId) {
		return jdbcTemplate.query(LIST_RECEIVE_LETTERS, letterRowMapper, receiverId);
	}
	
	/**
	 * 보낸 편지 목록 보기
	 */
	public Letter getSendLetter(String letterId, String senderId) {
		return jdbcTemplate.queryForObject(GET_SEND_LETTER, letterRowMapper,
				letterId, senderId);
	}
	
	/**
	 * 받은 편지 목록 보기
	 */
	public Letter getReceiveLetter(String letterId, String receiverId) {
		return jdbcTemplate.queryForObject(GET_RECEIVE_LETTER, letterRowMapper,
				letterId, receiverId);
	}
	
	/**
	 * 편지 보내기
	 */
	public int addLetter(Letter letter) {
		return jdbcTemplate.update(ADD_LETTER, letter.getTitle(),
				letter.getContent(), letter.getReceiverId(), letter.getRecitverName(),
				letter.getSenderId(), letter.getSenderName());
	}
	
	/**
	 * 받은 편지 삭제
	 */
	public int deleteArticle(String letterId, String receiverId) {
		return jdbcTemplate.update(DELETE_LETTER, letterId, receiverId);
	}
}
