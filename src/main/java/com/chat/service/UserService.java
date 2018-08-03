package com.chat.service;

import com.chat.Entity.User;


import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@CacheConfig(cacheNames = "UserServiceImpl")
public interface UserService {
	@Cacheable(keyGenerator="keyGenerator") 
    Page<User> findList(Pageable pageable);
    
	@Cacheable(keyGenerator="keyGenerator") 
    User findById(Integer id);
    
	@Cacheable(keyGenerator="keyGenerator") 
    User findByUserName(String userName);
    
	@CacheEvict(keyGenerator="keyGenerator",allEntries=true)
    Integer deleteById(Integer id);
	
	@CacheEvict(keyGenerator="keyGenerator",allEntries=true)
    void addUser(User user);
}
