package com.kwetter.services

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.SignatureException
import io.jsonwebtoken.UnsupportedJwtException
import java.util.Date
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter

object TokenService {

    private val signingKey = SecretKeySpec(
            DatatypeConverter.parseBase64Binary("secret"), SignatureAlgorithm.HS512.getJcaName()
    )

    fun createToken(email: String, role: String?, expired: Long): String {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .setExpiration(Date(expired))
                .compact()
    }

    @Throws(ExpiredJwtException::class,
            UnsupportedJwtException::class,
            MalformedJwtException::class,
            SignatureException::class,
            IllegalArgumentException::class)
    fun validateToken(token: String): String {
        val claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).body
        val role: String = claims.get("role").toString()
        println(role)
        Jwts.parser()
                .requireSubject(claims.subject)
                .setSigningKey(signingKey)
                .parseClaimsJws(token)

        return role
    }

    fun getEmailByToken(token: String): String? {
        var claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).body
        var subject = claims.subject
        return subject
    }
}