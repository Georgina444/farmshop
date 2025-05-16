package com.georgina.farmshop.service.impl;

import com.georgina.farmshop.configuration.JwtProvider;
import com.georgina.farmshop.domain.AccountStatus;
import com.georgina.farmshop.domain.UserRoles;
import com.georgina.farmshop.exceptions.SellerException;
import com.georgina.farmshop.model.Address;
import com.georgina.farmshop.model.Seller;
import com.georgina.farmshop.repository.AddressRepository;
import com.georgina.farmshop.repository.SellerRepository;
import com.georgina.farmshop.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;

    @Override
    public Seller getSellerProfile(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);

        return this.getSellerByEmail(email);
    }

    @Override
    public Seller createSeller(Seller seller) throws Exception {
        Seller sellerExists = sellerRepository.findByEmail(seller.getEmail());
        if (sellerExists != null) {
            throw new Exception("Seller already exists. Use a different email.");
        }
        // create new pickup address
        Address savedAddress = addressRepository.save(seller.getPickupAddress());

        Seller newSeller = new Seller();
        newSeller.setEmail(seller.getEmail());
        newSeller.setPassword(seller.getPassword());
        newSeller.setSellerName(seller.getSellerName());
        newSeller.setPickupAddress(seller.getPickupAddress());
        newSeller.setGSTIN(seller.getGSTIN());
        newSeller.setRole(UserRoles.ROLE_SELLER);
        newSeller.setPhoneNumber(seller.getPhoneNumber());
        newSeller.setBankDetails(seller.getBankDetails());
        newSeller.setBusinessDetails(seller.getBusinessDetails());

        return sellerRepository.save(newSeller);
    }

    @Override
    public Seller getSellerById(Long id) throws SellerException {
        return sellerRepository.findById(id)
                .orElseThrow(() -> new SellerException("Seller not found with this id." + id));
    }

    @Override
    public Seller getSellerByEmail(String email) throws Exception {
        Seller seller = sellerRepository.findByEmail(email);
        if (seller == null) {
            throw new Exception("Seller not found!");
        }
        return seller;
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus status) {
        return sellerRepository.findByAccountStatus(status);
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) throws Exception {
        Seller existingSeller = this.getSellerById(id);

        if (seller.getSellerName() != null) {
            existingSeller.setSellerName(seller.getSellerName());
        }
        if (seller.getPhoneNumber() != null) {
            existingSeller.setPhoneNumber(seller.getPhoneNumber());
        }
        if (seller.getEmail() != null) {
            existingSeller.setEmail(seller.getEmail());
        }
        if (seller.getBusinessDetails() != null
                && seller.getBusinessDetails().getBusinessName() != null
        ) {
            existingSeller.getBusinessDetails().setBusinessName(
                    seller.getBusinessDetails().getBusinessName()
            );
        }
        if (seller.getBankDetails() != null
                && seller.getBankDetails().getAccountHolderName() != null
                && seller.getBankDetails().getIfscCode() != null
                && seller.getBankDetails().getAccountNumber() != null
        ) {
            existingSeller.getBankDetails().setAccountHolderName(
                    seller.getBankDetails().getAccountHolderName()
            );
            existingSeller.getBankDetails().setAccountNumber(
                    seller.getBankDetails().getAccountNumber()
            );
            existingSeller.getBankDetails().setIfscCode(
                    seller.getBankDetails().getIfscCode()
            );
        }
        // MADE CHANGES HERE BECAUSE THE ADDRESS REFUSED TO UPDATE BECAUSE THE FIELDS WERE NULL FROM CREATION
        if (seller.getPickupAddress() != null) {
            Address incoming = seller.getPickupAddress();
            Address existing = existingSeller.getPickupAddress();
            if (incoming.getAddress()    != null) existing.setAddress(incoming.getAddress());
            if (incoming.getCity()       != null) existing.setCity(incoming.getCity());
            if (incoming.getCountry()    != null) existing.setCountry(incoming.getCountry());
            if (incoming.getPinCode()    != null) existing.setPinCode(incoming.getPinCode());
            if (incoming.getPhoneNumber()!= null) existing.setPhoneNumber(incoming.getPhoneNumber());
        }
{
            existingSeller.getPickupAddress()
                    .setAddress(seller.getPickupAddress().getAddress());
            existingSeller.getPickupAddress().setCity(seller.getPickupAddress().getCity());
            existingSeller.getPickupAddress().setCountry(seller.getPickupAddress().getCountry());
            existingSeller.getPickupAddress().setPhoneNumber(seller.getPickupAddress().getPhoneNumber());
            existingSeller.getPickupAddress().setPinCode(seller.getPickupAddress().getPinCode());
        }
        if (seller.getGSTIN() != null) {
            existingSeller.setGSTIN(seller.getGSTIN());
        }

        return sellerRepository.save(existingSeller);
    }

    @Override
    public void deleteSeller(Long id) throws Exception {
        Seller seller = getSellerById(id);
        sellerRepository.delete(seller);
    }

    @Override
    public Seller verifyEmail(String email, String otp) throws Exception {
        Seller seller = getSellerByEmail(email);
        seller.setEmailVerified(true);
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws Exception {
        Seller seller = getSellerById(sellerId);
        seller.setAccountStatus(status);
        return sellerRepository.save(seller);
    }
}
