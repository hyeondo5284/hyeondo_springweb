package org.hyeondo.letter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class LetterDao {
	
	//보낸 편지 갯수
	static final String COUNT_SEND_LETTERS = "select count(letterId) from letter where senderId=?";
	
	//받은 편지 갯수
	static final String COUNT_RECEIVE_LETTERS = "select count(letterId) from letter where receiverId=?";
	
	//보낸 편지 목록
	static final String LIST_SEND_LETTERS = "select letterId, title, receiverId, receiverName, cdate from letter where senderId=? order by letterId desc limit ?,?";
	
	//받은 편지 목록
	static final String LIST_RECEIVE_LETTERS = "select letterId, title, senderId, senderName, cdate from letter where receiverId=? order by letterId desc limit ?,?";

	//편지 조회
	static final String GET_LETTER = "select letterId, title, content, senderId, senderName, receiverId, receiverName, cdate from letter where letterId=? and (senderId=? or receiverId=?)";

	//편지 보내기
	static final String SEND_LETTER = "insert letter(title, content, senderId, senderName, receiverId, receiverName) values(?,?,?,?,?,?)";

	//편지 삭제
	static final String DELETE_LETTER = "delete from letter where letterId=? and (senderId=? or receiverId=?)";
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	RowMapper<Letter> letterRowMapper = new BeanPropertyRowMapper<>(Letter.class);
	
	/**
	 * 보낸 편지 목록 건수
	 */
	public int getSendLettersCount(String senderId) {
		return jdbcTemplate.queryForObject(COUNT_SEND_LETTERS, Integer.class, senderId);
	}
	
	/**
	 * 받은 편지 목록 건수
	 */
	public int getReceiveLettersCount(String receiverId) {
		return jdbcTemplate.queryForObject(COUNT_RECEIVE_LETTERS, Integer.class, receiverId);
	}
	
	/**
	 * 보낸 편지 목록
	 */
	public List<Letter> listSendLetters(String senderId, int offset, int count) {
		return jdbcTemplate.query(LIST_SEND_LETTERS, letterRowMapper, senderId, offset, count);
	}
	
	/**
	 * 받은 편지 목록
	 */
	public List<Letter> listReceiveLetters(String receiverId, int offset, int count) {
		return jdbcTemplate.query(LIST_RECEIVE_LETTERS, letterRowMapper, receiverId, offset, count);
	}
	
	/**
	 * 편지 조회
	 */
	public Letter getLetter(String letterId, String memberId) {
		return jdbcTemplate.queryForObject(GET_LETTER, letterRowMapper,
				letterId, memberId, memberId);
	}
	
	/**
	 * 편지 보내기
	 */
	public int sendLetter(Letter letter) {
		return jdbcTemplate.update(SEND_LETTER, letter.getTitle(),
				letter.getContent(), letter.getSenderId(), letter.getSenderName(),
				letter.getReceiverId(), letter.getReceiverName());
	}
	
	/**
	 * 받은 편지 삭제
	 */
	public int deleteLetter(String letterId, String memberId) {
		return jdbcTemplate.update(DELETE_LETTER, letterId, memberId, memberId);
	}
}
